package com.center.platform.controller;

import com.center.platform.entity.Menu;
import com.center.platform.entity.User;
import com.center.platform.service.IRoleService;
import com.center.platform.service.IUserService;
import com.center.platform.utils.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/11/24
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private AuthenticationManager myAuthenticationManager;

    @RequestMapping("/index")
    public String login(HttpServletRequest request,
                        HttpSession session,
                        User user,
                        Model model){
        User loginUser ;
        if(isNotNull(session.getAttribute("user"))){
            loginUser = (User) session.getAttribute("user");
        }else{
            if(isNotNull(user.getName())){
                loginUser= userService.validUser(user);
            }else{
                model.addAttribute("error","用户名或密码无效！");
                return "redirect:../";
            }
        }
        if (isNotNull(loginUser)) {
            authenticationUser(loginUser,request);
            session.setAttribute("user", loginUser);
            List<Menu> lst = userService.getMenuLst(loginUser);
            model.addAttribute("menuResult", lst);
            return "index";
        } else {
            return "redirect:/?error=" + EnumUtils.ERROR_TAG.loginError.name();
        }
    }

    /**
     * 查询用户信息
     * @param where
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object query(@RequestParam(value = "where", required = false, defaultValue = "1=1") String where,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            return userService.queryUsers(where,page,size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/roleInfo")
    @ResponseBody
    public Object roleInfo(){
        return roleService.find();
    }

    /**
     * 将用户信息放入spring security中
     * @param user
     */
    private void authenticationUser(User user,HttpServletRequest request){
        Authentication authentication = myAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

}

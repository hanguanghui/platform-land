package com.center.platform.controller;

import com.center.platform.entity.Menu;
import com.center.platform.entity.User;
import com.center.platform.service.IRoleService;
import com.center.platform.service.IUserService;
import com.center.platform.utils.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

}

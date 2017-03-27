package com.center.platform.controller;

import com.center.platform.event.JSONMessageException;
import com.center.platform.utils.EnumUtils;
import com.center.platform.utils.EnumUtils.ERROR_TAG;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.center.platform.utils.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;

@Controller
public class SystemController extends BaseController
{
    @RequestMapping({"/"})
    public String load(HttpServletRequest request, HttpSession session, @RequestParam(value="error", required=false) String error, Model model)
    {
        if ((error != null) &&
                (error.equalsIgnoreCase(EnumUtils.ERROR_TAG.loginError.name()))) {
            model.addAttribute("error", "用户名或密码错误！");
        }
        if (isNotNull(session.getAttribute("user"))) {
            session.removeAttribute("user");
        }
        return "login";
    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session){
        if (isNotNull(session.getAttribute("user"))) {
            session.removeAttribute("user");
        }
        return "login";
    }
    @RequestMapping("/main")
    public String main(HttpServletRequest request, Model model)
    {
        return "/maintable";
    }

    @RequestMapping("/test")
    public String test(){return "/test";}

    @RequestMapping("/upload")
    public String upload(){return "/upload";}

    /**
     * proxy
     * @param request
     * @return
     */
    @RequestMapping(value = "/proxy")
    @ResponseBody
    public Object proxy(@RequestParam(value = "dataType", defaultValue = "text") String dataType,
                        @RequestParam(value = "requestUrl", defaultValue = "") String requestUrl,
                        HttpServletRequest request) {
        String uri;
        String temp ="1";
        System.out.println(temp);
        try {
            uri = StringUtils.isNotBlank(requestUrl)?requestUrl: request.getQueryString();
            String url=uri.split("\\?")[0];
            return HttpRequest.post(url, HttpRequest.parseParam(uri, request.getParameterMap()), dataType);
        } catch (UnsupportedEncodingException e) {
            throw new JSONMessageException(e.getLocalizedMessage());
        } catch (Exception ex) {
            throw new JSONMessageException(ex.getLocalizedMessage());
        }
    }

}
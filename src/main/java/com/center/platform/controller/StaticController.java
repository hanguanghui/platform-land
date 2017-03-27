package com.center.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hanguanghui
 * @version V1.0, 2017/3/3
 * @Description **
 * @project platform
 */
@Controller
@RequestMapping("/staticService")
public class StaticController {

    @RequestMapping("/country")
    public String Country(){
        return "/static/country";
    }

    @RequestMapping("/depart")
    public String depart(){
        return "/static/depart";
    }
    @RequestMapping("/project")
    public String project(){
        return "/static/project";
    }
    @RequestMapping("/multi")
    public String multi(){
        return "/static/multi";
    }
    @RequestMapping("/dynamic")
    public String dynamic(){
        return "/static/dynamic";
    }

}

package com.center.platform.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hangu on 2016/9/23.
 */


public class BaseController {
    public void print(String o){
        System.out.println(o);
    }

    public boolean isNotNull(Object value){
        if (value != null) return true;
        if (value instanceof String) return StringUtils.isNotBlank((String) value);
        return false;
    }

    /**
     * 结果集合utf编码
     * @param value
     * @param response
     */
    protected void result(Object value, HttpServletResponse response) {
        Map result = new HashMap();
        result.put("result", value);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

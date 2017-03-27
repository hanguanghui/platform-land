package com.center.platform.controller;

import com.center.platform.event.JSONMessageException;
import com.center.platform.service.IWeatherService;
import com.center.platform.utils.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author hanguanghui
 * @version V1.0, 2017/3/6
 * @Description **
 * @project platform
 */
@Controller
@RequestMapping("/utilService")
public class UtilController extends BaseController {

    @Autowired
    private IWeatherService weatherService;


    @RequestMapping(value="/getWeather")
    @ResponseBody
    public void weatherInfo(@RequestParam(value = "city",required = true)String city,@RequestParam(value = "code",required = true)String code,HttpServletResponse response){
        result(weatherService.getWeather(city,code),response);
    }

    @RequestMapping(value = "/proxy")
    @ResponseBody
    public Object proxy(@RequestParam(value = "dataType", defaultValue = "text") String dataType,
                        @RequestParam(value = "requestUrl", defaultValue = "") String requestUrl,
                        HttpServletRequest request) {
        String uri;
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

package com.center.platform.service;

import java.util.Map;

/**
 * @author hanguanghui
 * @version V1.0, 2017/3/6
 * @Description **
 * @project platform
 */
public interface IWeatherService {

     Map getWeather(String city,String code);

     String  parseHtml(String url);

    }

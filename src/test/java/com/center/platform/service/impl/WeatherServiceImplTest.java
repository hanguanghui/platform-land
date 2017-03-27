package com.center.platform.service.impl;

import com.center.platform.BaseServiceTest;
import com.center.platform.service.IWeatherService;
import com.fasterxml.jackson.core.JsonParser;
import junit.framework.TestCase;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherServiceImplTest extends BaseServiceTest {

    @Autowired
    private IWeatherService weatherService;

    @Test
    public void testBy()throws Exception {
        //weatherService.getCityWeather("滁州");
    }
}
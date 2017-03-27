package com.center.platform.services.impl;

import com.alibaba.fastjson.JSON;
import com.center.platform.BaseServiceTest;
import com.center.platform.Constant;
import com.center.platform.service.IWeatherService;
import com.center.platform.utils.HttpRequest;
import com.center.platform.utils.Utils;
import com.center.platform.utils.WeatherUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanguanghui
 * @version V1.0, 2016/9/26
 */
public class test extends BaseServiceTest {

    @Autowired
    private IWeatherService weatherService;

    @Test
    public void valid() throws Exception{
        print(String.valueOf(true));
    }

    @Test
    public void testBy()throws Exception{
      // String t= Utils.getDatechar();
        WeatherUtil.getWeather("滁州");
        //weatherService.getWeather("111");
        /*String url="http://platform.sina.com.cn/weather/forecast?app_key=1315597423&city=天长";

        Object c = HttpRequest.post(url,new HashMap(),null);
        print(c.toString());*/
    }
    @Test
    public void getData()throws Exception{
        String path = "D://cit.txt";
        List<Map> list = new ArrayList<Map>();
        String txt = getGeojson(path);
        if(txt!= null){
            String[] ts = txt.split(";");
            for(int i=0;i<ts.length;i++){
                String x = ts[i];
                String[] xs=x.split(",");
                Map map = new HashMap();
                map.put("provience",xs[3]);
                map.put("city",xs[2]);
                map.put("country",xs[1]);
                map.put("code",xs[0]);
                list.add(map);
            }
        }
        print(JSON.toJSONString(list));
    }

    public String getGeojson(String path) throws Exception {
        File filename = new File(path); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename),"gbk"); // 建立一个输入流对象reader
//        String txt = IOUtils.read();
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        line = br.readLine();
        while (br.readLine() != null) {
            line += br.readLine(); // 一次读入一行数据
        }
        return line;
    }

    @Test
    public void getWeath(){

    }
}

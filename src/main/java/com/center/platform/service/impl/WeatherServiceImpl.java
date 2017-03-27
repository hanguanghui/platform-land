package com.center.platform.service.impl;

import com.center.platform.service.IWeatherService;
import com.center.platform.utils.WeatherUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hanguanghui
 * @version V1.0, 2017/3/6
 * @Description **
 * @project platform
 */
@Service
public class WeatherServiceImpl extends BaseLogger implements IWeatherService {
    @Override
    public Map getWeather(String city,String code) {
        Map resultMap = new HashMap();

        //网络爬虫 获取天气信息
        String result = get("http://www.weather.com.cn/weather1d/"+code+".shtml");
        Document doc = Jsoup.parse(result);
        Element content = doc.getElementById("hidden_title");
        Elements wea = doc.getElementsByClass("wea");
        Elements tem = doc.getElementsByClass("tem");
        Elements sunUp = doc.getElementsByClass("sunUp");
        Elements sunDown = doc.getElementsByClass("sunDown");

        //获取白天 夜间的天气 温度
        String dayWea = wea.get(0).html()+"  "+tem.get(0).childNode(0).childNode(0)+"°C";
        String nightWea = wea.get(1).html()+"  "+tem.get(1).childNode(0).childNode(0)+"°C";

        String daySunUp = sunUp.get(0).html();
        String daySunDown = sunDown.get(0).html();

        //组织白天 夜间天气返回结果
        resultMap.put("dayWea",dayWea);
        resultMap.put("nightWea",nightWea);
        resultMap.put("daySunUp",daySunUp);
        resultMap.put("daySunDown",daySunDown);

        String value =content.val();
        //获取天气信息
        String day =value.split(" ")[1].substring(1,2);
        String weather = value.split(" ")[3];
        //通过webseivice 获取天气详细
       /* String x = WeatherUtil.getWeather(city);
        String[] weatherDetails=x.split(":")[2].split(";");
        String temperature =weatherDetails[0].split("：")[2];
        String direction = weatherDetails[1].split("：")[1].split(" ")[0];
        String power = weatherDetails[1].split("：")[1].split(" ")[1];*/

        //组织天气概述情况
        resultMap.put("weather",weather);
        resultMap.put("day",day);
       /* resultMap.put("temperature",temperature);
        resultMap.put("direction",direction);
        resultMap.put("power",power);*/

        return resultMap;
    }

    @Override
    public String parseHtml(String url) {
        return "";
    }

    /**
     * get 方法
     * @param url
     * @return
     */
    public static String get(String url){
        String result = "";
        try {
            //获取httpclient实例
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //获取方法实例。GET
            HttpGet httpGet = new HttpGet(url);
            //执行方法得到响应
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                //如果正确执行而且返回值正确，即可解析
                if (response != null
                        && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println(response.getStatusLine());
                    HttpEntity entity = response.getEntity();
                    //从输入流中解析结果
                    result = readResponse(entity, "utf-8");
                }
            } finally {
                httpclient.close();
                response.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * stream读取内容，可以传入字符格式
     * @param resEntity
     * @param charset
     * @return
     */
    private static String readResponse(HttpEntity resEntity, String charset) {
        StringBuffer res = new StringBuffer();
        BufferedReader reader = null;
        try {
            if (resEntity == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(
                    resEntity.getContent(), charset));
            String line = null;

            while ((line = reader.readLine()) != null) {
                res.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }
}

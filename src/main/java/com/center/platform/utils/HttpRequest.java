package com.center.platform.utils;

import com.alibaba.fastjson.JSON;
import com.center.platform.Constant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhangbixi
 * Date: 13-4-19
 * Time: 下午2:42
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequest {

    private static final int DEFAULT_TIME_OUT = 30000;

    private static String zhPattern = "[\\u4e00-\\u9fa5]+";


    public enum RES_DATA_TYPE {
        text, json, bytes
    }


    /**
     * get
     *
     * @param url
     * @param queryString
     * @return
     */
    public final static Object get(String url, String queryString,String dataType) {
        if (StringUtils.isBlank(url)) throw new RuntimeException("request url is null");
        HttpClient http = new HttpClient();
        HttpMethod get = new GetMethod(url);
        if(StringUtils.isNotBlank(queryString)) get.setQueryString(queryString);
        return request(http, get,dataType);
    }

    /**
     * post
     *
     * @param url
     * @param data
     * @return
     */
    public final static Object post(String url, NameValuePair[] data,String dataType) {
        if (StringUtils.isBlank(url)) throw new RuntimeException("request url is null");
        HttpClient http = new HttpClient();
        HttpMethod post = new PostMethod(url);
        if (data != null && data.length > 0) ((PostMethod) post).addParameters(data);
        return request(http, post,dataType);
    }

    /**
     * post
     *
     * @param url
     * @param data
     * @return
     */
    public final static Object post(String url, Map data,String dataType) {
        if (StringUtils.isBlank(url)) throw new RuntimeException("request url is null");
        if (StringUtils.isBlank(url)) throw new RuntimeException("request url is null");
        HttpClient http = new HttpClient();
        HttpMethod post = null;
        try {
            post = new PostMethod(encode(url, Constant.UTF_8));
            if (data != null && !data.isEmpty()) {
                for (Object item : data.entrySet()){
                    Map.Entry entry = (Map.Entry) item;
                    Object value = entry.getValue();
                    ((PostMethod) post).addParameter((String)entry.getKey(),value instanceof String[]?((String[])entry.getValue())[0]:value.toString());
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        Object o =  request(http, post,dataType);
        return o;
    }
    /**
     * 向Web服务器请求数据
     *
     * @param urlString
     * @param postString
     * @return
     * @throws Exception
     */
    public static final String sendRequest(String urlString, String postString) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", Constant.UTF_8);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(DEFAULT_TIME_OUT);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            byte[] postByte = new byte[1];
            int postLength = 0;
            if (StringUtils.isNotBlank(postString)) {
                postByte = postString.getBytes(Constant.UTF_8);  //Constant.UTF_8
                postLength = postByte.length;
            }
            connection.getOutputStream().write(postByte, 0, postLength);
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            if (connection.getResponseCode() == 200) {
                return getStringFromInputStream(connection.getInputStream(), Constant.UTF_8);
            }
            String statusMsg = "{" + connection.getResponseCode() + " - " + connection.getResponseMessage() + "}";
            throw new Exception("Http请求异常：" + statusMsg);
        } catch (Exception e) {
            throw new Exception("Http请求异常：" + e.getLocalizedMessage());
        } finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

    /**
     * 从输入流中读取内容
     *
     * @param is
     * @param encode
     * @return
     */
    public static final String getStringFromInputStream(InputStream is, String encode) {
        try {
            InputStreamReader isr = new InputStreamReader(is, encode != null ? encode : Constant.UTF_8);
            StringBuffer sb = new StringBuffer("");
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            while (str != null) {
                sb.append(str);
                str = br.readLine();
            }
            return sb.toString();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 请求服务
     *
     * @param urlString
     * @param postString
     * @return
     * @throws Exception
     */
    public static final String sendRequest2(String urlString, String postString) throws Exception {
        String result = "";
        HttpClient http = new HttpClient();
        HttpMethod method = new GetMethod(urlString);
        method.setRequestHeader("Content-Type", Constant.UTF_8);
        if (StringUtils.isNotBlank(postString)) method.setQueryString(postString);
        try {
            int status = http.executeMethod(method);
            result = IOUtils.toString(method.getResponseBodyAsStream(),Constant.UTF_8);
            if (HttpStatus.SC_OK == status) {
                return result;
            }
        } catch (Exception e) {
            result = e.getLocalizedMessage();
        } finally {
            method.releaseConnection();
        }
        throw new Exception("Http请求异常：" + result);
    }

    /***
     *
     * @param str
     * @param charset
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String encode(String str, String charset)
            throws UnsupportedEncodingException {
        str = str.replaceAll(" ", "+");// 对空字符串进行处理
        Pattern p = Pattern.compile(zhPattern);
        Matcher m = p.matcher(str);
        StringBuffer b = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
        }
        m.appendTail(b);
        return b.toString();
    }


    /***
     *
     * @param uri
     * @return
     * @throws Exception
     */
    public static final Map parseUriParams(String uri) throws Exception {
        Map map = new HashMap();
        try {
            if (StringUtils.isNotBlank(uri)) {
                String[] strs = uri.split("\\?");
                if (strs.length > 1) {
                    String paramStr = strs[1];
                    if (StringUtils.isNotBlank(paramStr)) {
                        String[] params = paramStr.split("&");
                        for (int i = 0; i < params.length; i++) {
                            String param = params[i];
                            if (StringUtils.isNotBlank(param) && StringUtils.contains(param, "=")) {
                                String[] tmp = param.split("=");
                                map.put(tmp[0], tmp[1]);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("parse uri params exception{}", e);
        }
        return map;
    }

    /***
     * parse all request param
     * @param uri
     * @param parameterMap
     * @return
     * @throws Exception
     */
    public static final Map parseParam(String uri, Map parameterMap) throws Exception {
        Map map = new HashMap();
        Map uriParam = parseUriParams(uri);
        if (!uriParam.isEmpty())
            map.putAll(uriParam);
        if (!parameterMap.isEmpty()) {
            for (Object key : parameterMap.keySet()) {
                Pattern pattern = Pattern.compile("^http\\:\\/\\/.+$");
                Matcher matcher = pattern.matcher((String) key);
                if (matcher.matches()) continue;
                map.put(key, parameterMap.get(key));
            }
        }
        return map;
    }

    /**
     * request
     *
     * @param http
     * @param method
     * @return
     */
    private static Object request(HttpClient http, HttpMethod method, String dataType) {
        String result;
        method.getParams().setSoTimeout(DEFAULT_TIME_OUT);
        method.getParams().setContentCharset(Constant.UTF_8);
        method.getParams().setUriCharset(Constant.UTF_8);
        try {
            int status = http.executeMethod(method);
            byte[] bytes = method.getResponseBody();
            result = IOUtils.toString(bytes, Constant.UTF_8);
            if (org.apache.http.HttpStatus.SC_OK == status) {
                try {
                    if (StringUtils.isBlank(dataType)) return result;
                    switch (RES_DATA_TYPE.valueOf(dataType)) {
                        case json:
                            return JSON.parse(result);
                        case bytes:
                            return bytes;
                        default:
                            return result;
                    }
                } catch (IllegalArgumentException e) {
                    return result;
                }
            }
        } catch (Exception e) {
            result = e.getLocalizedMessage();
        } finally {
            method.releaseConnection();
        }
        throw new RuntimeException("网络请求异常: [" + result + "]");
    }
}
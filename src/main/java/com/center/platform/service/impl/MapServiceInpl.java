package com.center.platform.service.impl;

import com.center.platform.service.IMapService;
import com.center.platform.utils.HttpRequest;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @author hanguanghui
 * @version V1.0, 2017/2/21
 * @Description **
 * @project platform
 */
@Service
public class MapServiceInpl extends BaseLogger implements IMapService {

    private String tempLocation;
    private String parseShapeInterface = "";
    private String serverUrl="";

    /**
     * @param request
     * @param realPath
     * @return
     */
    @Override
    public Object parseShape(MultipartHttpServletRequest request, String realPath) throws IOException {
        Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile files = request.getFile(iterator.next());
            String fileName = files.getOriginalFilename();
            File file = new File(realPath+tempLocation+"\\"+fileName);
            FileUtils.copyInputStreamToFile(files.getInputStream(), file);
            String localUrl = serverUrl+"/land/"+tempLocation+"/"+fileName;
            try{
                String reqUrl = parseShapeInterface;
                Map params = Maps.newHashMap();
                params.put("urlPath",localUrl);

                return String.valueOf(HttpRequest.post(reqUrl,params,null));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public String getTempLocation() {
        return tempLocation;
    }

    public void setTempLocation(String tempLocation) {
        this.tempLocation = tempLocation;
    }

    public String getParseShapeInterface() {
        return parseShapeInterface;
    }

    public void setParseShapeInterface(String parseShapeInterface) {
        this.parseShapeInterface = parseShapeInterface;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}

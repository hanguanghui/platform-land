package com.center.platform.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author hanguanghui
 * @version V1.0, 2017/2/21
 * @Description **
 * @project platform
 */
public interface IMapService {
    Object parseShape(MultipartHttpServletRequest request,String realPath) throws IOException;
}

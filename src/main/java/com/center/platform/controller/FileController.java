package com.center.platform.controller;

import com.alibaba.fastjson.JSON;
import com.center.platform.entity.Material;
import com.center.platform.service.IAssignmentService;
import com.center.platform.service.IFileService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.center.platform.service.IMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping({"/fileService"})
public class FileController extends BaseController
{

    @Autowired
    private IFileService fileService;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IMapService mapService;
    /**
     * 编制材料等 上传文件（word doc docx）
     * @param proid
     * @param request
     * @param session
     * @param response
     */
    @RequestMapping(value={"/upload/{proid}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void upload(@PathVariable String proid, MultipartHttpServletRequest request, HttpSession session, HttpServletResponse response)
    {
        try
        {
            String pdfPath = session.getServletContext().getRealPath("/").concat("\\static\\");
            result(JSON.toJSONString(this.fileService.save(request, proid, pdfPath)), response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param request
     * @param session
     * @param response
     */
    @RequestMapping(value = "/zip/upload")
    @ResponseBody
    public Object zipUpload(MultipartHttpServletRequest request, HttpSession session, HttpServletResponse response) {
        try {
            String realPath = session.getServletContext().getRealPath("/").concat("\\static\\");
            Object o= mapService.parseShape(request,realPath);
            System.out.print(String.valueOf(o));
            return o;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据项目编号获取已上传文件信息
     * @param proid
     * @return
     */
    @RequestMapping({"/getFileList"})
    @ResponseBody
    public List<Material> getFileLst(@RequestParam(value="proid", required=true) String proid) { Material ma = new Material();
        ma.setProid(proid);
        return this.fileService.find(ma); }

    /**
     * 删除数据 同时删除已上传word以及pdf
     * @param fileid
     * @param session
     * @return
     */
    @RequestMapping({"/deleteFile"})
    @ResponseBody
    public Map deleteFile(@RequestParam(value="fileid", required=true) String fileid, HttpSession session) {
        Map map = new HashMap();
        String pdfPath = session.getServletContext().getRealPath("/").concat("\\static\\");
        if (this.fileService.delete(fileid, pdfPath))
            map.put("success", Boolean.valueOf(true));
        else {
            map.put("success", Boolean.valueOf(false));
        }
        return map;
    }

    /**
     *
     * @param parentId
     * @return
     */
    @RequestMapping({"/getFile/{parentId}"})
    @ResponseBody
    public String[] ids(@PathVariable String parentId)
    {
        try
        {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
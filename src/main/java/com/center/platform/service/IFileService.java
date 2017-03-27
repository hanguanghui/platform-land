package com.center.platform.service;

import com.center.platform.entity.Material;
import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public abstract interface IFileService
{
    /**
     * 查询编制材料
     * @param paramMaterial
     * @return
     */
    public abstract List<Material> find(Material paramMaterial);

    /**
     *  编制单位办结前验证是否上传材料
     * @param paramString
     * @return
     */
    public abstract boolean validFileSize(String paramString);

    /**
     * 保存处理上传的编制材料
     * @param paramMultipartHttpServletRequest
     * @param paramString1
     * @param paramString2
     * @return
     */
    public abstract Material save(MultipartHttpServletRequest paramMultipartHttpServletRequest, String paramString1, String paramString2);

    /**
     * 转换 word to pdf
     * @param sourceFile
     * @param destFile
     * @return
     */
    public abstract File convert2pdf(String sourceFile, String destFile);

    /**
     * 删除
     * @param fileid
     * @param realPath 服务器存储路径 删除原文件
     * @return
     */
    public abstract boolean delete(String fileid, String realPath);

    /**
     *  复制文件到服务器路径下 并转换word至pdf
     * 如果文件为pdf 则不进行转换
     * @param files      上传文件
     * @param wordFile   备份文件
     * @param type       文件类型
     * @param proId      项目id 用于创建文件夹 统一管理文件（编制、施工、监理日志等）
     * @param pdfPath    在线pdf地址
     * @return
     */
    File copyFile(MultipartFile files,File wordFile,String type,String proId,String pdfPath);

    /**
     * 创建新的文件
     * @param name
     * @param type
     * @param proid
     * @return
     */
    File getNewFile(String name,String type,String proid);
}
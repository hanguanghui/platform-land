package com.center.platform.service.impl;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.center.platform.dao.IFileDao;
import com.center.platform.entity.Material;
import com.center.platform.service.IFileService;
import com.center.platform.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author hanguanghui
 * @version V1.0, 2016/9/28
 */
@Service
public class FileServiceImpl extends BaseLogger implements IFileService {

    @Autowired
    private IFileDao filedao;


    private String localLocation;
    private String pdfLocation;
    private String host;
    private String port;
    private String openOfficeHome;
    private OpenOfficeConnection openOfficeConnection;
    private String fileSize;

    @Override
    public List<Material> find(Material ma) {
        return filedao.find(ma);
    }

    @Override
    public boolean validFileSize(String proId) {
        if(isNotNull(proId)){
            Material ma = new Material();
            ma.setProid(proId);
            List<Material> lst = this.find(ma);
            double value= Double.valueOf(fileSize);
            if(lst.size() > value||lst.size() == value) {
                return true;
            }else {
                return false;
            }
        }else
            return false;
    }

    /**
     * 保存 上传文件
     * @param request
     * @param proId
     * @param pdfPath
     * @return
     */
    @Override
    public Material save(MultipartHttpServletRequest request, String proId,String pdfPath) {
        try{
            Material ma = new Material();
            Iterator<String> iterator = request.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile files = request.getFile(iterator.next());
                String fileName = files.getOriginalFilename();
                String wordFilePath = localLocation.concat("doc").concat("\\" + proId + "\\" + fileName);

                File wodrFile = getNewFile(fileName,"doc",proId);
                File pdfFile = copyFile(files,wodrFile,"doc",proId,pdfPath);
                if(pdfFile.exists())
                    ma = save2DB2(wordFilePath,"\\"+pdfLocation.concat("\\"+proId).concat("\\"+pdfFile.getName()),proId,fileName,files.getSize());
                if(ma!=null)
                    return ma;
            }
            return ma;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     *  复制文件到服务器路径下 并转换word至pdf
     * 如果文件为pdf 则不进行转换
     * @param files      上传文件
     * @param wordFile   备份文件名
     * @param type       文件类型
     * @param proId      项目id 用于创建文件夹 统一管理文件（编制、施工、监理日志等）
     * @param pdfPath    在线pdf地址
     * @return
     */
    public File copyFile(MultipartFile files,File wordFile,String type,String proId,String pdfPath){
        try{
            FileUtils.copyInputStreamToFile(files.getInputStream(), wordFile);
            if("doc".equals(type)){
                //covert word to pdf
                return convert2pdf(wordFile.getPath(), pdfPath.concat(pdfLocation).concat("\\" + proId));
            }else if("pdf".equals(type)){
                File file =null;
                String path = pdfPath.concat(pdfLocation).concat("\\" + proId);
                File folder = new File(path);
                if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
                file = new File(path.concat("/" + wordFile.getName()));
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                FileUtils.copyInputStreamToFile(files.getInputStream(), file);
                return file;
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Material save2DB2(String wordPath,String pdfPath,String proId,String fileName,long size){
        Material ma = new Material();
        ma.setCreatetime(Utils.getStatesTime(0));
        ma.setFileid(Utils.getRandomString());
        ma.setFilesize(size);
        ma.setPath(wordPath);
        ma.setPdfpath(pdfPath);
        ma.setProid(proId);
        ma.setFilename(fileName);

        boolean istrue =filedao.save(ma);
        if(istrue)
            return ma;
        else
            return null;
    }

    @Override
    public File convert2pdf(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            //valid the file
            if (!inputFile.exists()) {
                return null;
            }
            //get pdf full path
            String fileName = inputFile.getName();
            String name = fileName.substring(0,fileName.lastIndexOf("."));

            //MD5编码
            String fullPdfPath = destFile.concat("//"+Utils.EncoderByMd5(name)+".pdf");

            //valid the out file is exit, if not create it
            File outputFile = new File(fullPdfPath);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            OpenOfficeConnection connection = getOpenOfficeConnection();
            connection.connect();
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            // close the connection
            connection.disconnect();
            //return
            return new File(fullPdfPath);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String fileid,String realPath) {
        Material ma =new Material();
        ma.setFileid(fileid);
        List<Material> lst = this.find(ma);
        boolean isTrue = false;
        if(isNotNull(lst)&&lst.size()>0){
            ma = lst.get(0);
            isTrue = this.deleteServerFile(ma, realPath);
            isTrue = filedao.delete(fileid);
        }
        return isTrue;
    }

    public boolean deleteServerFile(Material ma,String realPath) {
        try{
            File wordFile = new File(ma.getPath());
            if(wordFile.exists())
                wordFile.delete();
            File pdfFile = new File(realPath+ma.getPdfpath());
            if(pdfFile.exists())
                pdfFile.delete();
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * get file by fileName
     * @param name 文件名
     * @return File
     */
    public File getNewFile(String name,String type,String proid) {
        File file = null;
        try {
            String path =localLocation.concat(type).concat("\\" + proid);
            File folder = new File(path);
            if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
            file = new File(path.concat("/" + name));
            if (file.exists()) {
                file = new File(path.concat("/1_" + name));
                //file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return file;
    }

    /**
     * get openoffice connection
     * @return OpenOfficeConnection
     * @throws IOException
     */
    private OpenOfficeConnection getOpenOfficeConnection() throws IOException {
        try {
            // 启动OpenOffice的服务
            if(openOfficeConnection!=null)
                return openOfficeConnection;
            else{
                String command = openOfficeHome.concat("\\soffice.exe -headless -accept=\"socket,host=" + host + ",port=" + port + ";urp;\"");
                Runtime.getRuntime().exec(command);
                openOfficeConnection = new SocketOpenOfficeConnection(host, Integer.valueOf(port));
            }
            return openOfficeConnection;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void setLocalLocation(String localLocation) {
        this.localLocation = localLocation;
    }

    public void setPdfLocation(String pdfLocation) {
        this.pdfLocation = pdfLocation;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setOpenOfficeHome(String openOfficeHome) {
        this.openOfficeHome = openOfficeHome;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}

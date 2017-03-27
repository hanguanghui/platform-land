package com.center.platform.service.impl;

import com.center.platform.dao.IConstructLogDao;
import com.center.platform.dao.ILogDao;
import com.center.platform.dao.ISupervisionDao;
import com.center.platform.dao.ISupervisionLogDao;
import com.center.platform.entity.ConstructLog;
import com.center.platform.entity.Log;
import com.center.platform.entity.Supervision;
import com.center.platform.entity.SupervisionLog;
import com.center.platform.service.IFileService;
import com.center.platform.service.ILogService;
import com.center.platform.utils.EnumUtils;
import com.center.platform.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by hangu on 2017/1/2.
 */
@Service
public class LogServiceImpl extends BaseLogger implements ILogService {

    @Autowired
    private ILogDao logDao;

    // 监理日志详细
    @Autowired
    ISupervisionDao supervisionDao;

    //监理日志概要
    @Autowired
    ISupervisionLogDao supervisionLogDao;

    @Autowired
    IConstructLogDao constructLogDao;

    @Autowired
    IFileService fileService;

    private String logPath;

    /**
     * 保存图片到数据库（监理图片）
     * @param log
     * @return
     */
    @Override
    public boolean sava2DB(Log log) {
        return logDao.insert(log);
    }

    /**
     * 删除监理图片
     * @param logId
     * @return
     */
    @Override
    public boolean delete(String logId) {
        return logDao.delete(logId);
    }

    /**
     * 保存日志信息（监理图片信息）
     * @param request
     * @param proid
     * @param path
     * @param type
     * @return
     */
    @Override
    public Log sava(MultipartHttpServletRequest request, String proid, String path,String type) {
        try {
            Log log = new Log();
            Iterator<String> iterator = request.getFileNames();
            while (iterator.hasNext()) {
                //处理图片
                if(type.equalsIgnoreCase(EnumUtils.LOG_TYPE.pic.name())){
                    //直接保存到文件夹下的/pic/proid/origin/time.jpg
                    MultipartFile files = request.getFile(iterator.next());
                    String fileName = files.getOriginalFilename();
                    File picFile = getNewFile(fileName.substring(fileName.lastIndexOf("."), fileName.length()),path,proid);
                    FileUtils.copyInputStreamToFile(files.getInputStream(), picFile);

                    picFile = new File(picFile.getPath());
                    //压缩图片
                    String thumPath = path.concat(logPath+"\\thum\\"+proid+"\\"+picFile.getName());
                    Utils.zoomImageScale(picFile,thumPath,120);

                    log.setName(fileName);
                    log.setProid(proid);
                    log.setCreatetime(Utils.getStatesTime(0));
                    log.setId(Utils.getRandomString());
                    log.setName(picFile.getName());
                    log.setOrdernumber(picFile.getName().substring(0,picFile.getName().lastIndexOf(".")));
                    log.setPath("");
                    log.setServerpath("/land/"+picFile.getPath().substring(picFile.getPath().indexOf(logPath.replace("/","\\")),picFile.getPath().length()));
                    log.setThumb("/land/"+thumPath.substring(thumPath.indexOf(logPath),thumPath.length()));
                    log.setType(type);
                }else if(type.equalsIgnoreCase(EnumUtils.LOG_TYPE.doc.name())||type.equalsIgnoreCase(EnumUtils.LOG_TYPE.pdf.name())){
                    Log fileLog = new Log();

                    //遍历文件信息
                    while (iterator.hasNext()) {
                        MultipartFile files = request.getFile(iterator.next());
                        String fileName = files.getOriginalFilename();
                        File wordFile = fileService.getNewFile(fileName,type,proid);
                        File file = fileService.copyFile(files,wordFile,type,proid,path);
                        log.setName(fileName);
                        log.setProid(proid);
                        log.setCreatetime(Utils.getStatesTime(0));
                        log.setId(Utils.getRandomString());
                        log.setOrdernumber(file.getName().substring(0,file.getName().lastIndexOf(".")));
                        log.setPath(wordFile.getPath());
                        log.setServerpath("/land" + file.getPath().substring(file.getPath().indexOf("static") + 6, file.getPath().length()));
                        log.setThumb("");
                        log.setType(type);
                    }
                }

                boolean istrue = sava2DB(log);
                List<Log> logs = logDao.find(log);
                if(isNotNull(logs)&&logs.size()>0){
                    return logs.get(0);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 根据年份查询
     * @param proid
     * @return
     */
    @Override
    public List<String> findYears(String proid) {
        Log log = new Log();
        log.setProid(proid);
        return logDao.findYears(log);
    }

    /**
     * 根据时间查询
     * @param proid
     * @param year
     * @param month
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public Page queryTasks(String proid,String year,String type,String month, int pageNum, int size) {

        List condition = new ArrayList();
        Log log = new Log();
        log.setProid(proid);
        String[] types = type.split(",");
        if(types.length>1){
            String typeCon ="";
            for (int i=0;i<types.length;i++) {
                typeCon+=" TYPE = '"+types[i]+"'";
                if(i<types.length-1){
                    typeCon+=" OR ";
                }
            }
            condition.add(typeCon);
        }else{
            log.setType(type);
        }

        condition.add("to_char(createtime,'yyyy-mm')='"+year.concat("-")+(Integer.valueOf(month) <10?"0"+month:month)+"'");
        log.setConditions(condition);

        List<Log> logs = this.logDao.find(log);
        int total = logs.size();
        int start = (pageNum - 1) * size;
        int end = size==0?total:pageNum * size > total ? total : pageNum * size;
        size = size==0?total:size;
        logs = logs.subList(start, end);

        return new PageImpl(logs, new PageRequest(pageNum, size), total);
    }

    /**
     * 保存监理信息（监理日志 监理详细）
     * @param lst
     * @param supervisionLog
     * @return
     */
    @Override
    public boolean saveSupervisionLog(List<Supervision> lst, SupervisionLog supervisionLog) {
        boolean isTrue =false;
        try{
            if(isNotNull(supervisionLog)){
                supervisionLog.setCreatetime(Utils.getStatesTime(0));
                supervisionLog.setSupervisionlogid(Utils.getRandomString());

                isTrue = supervisionLogDao.save(supervisionLog);

                for (Supervision supervision : lst) {
                    supervision.setDetailid(Utils.getRandomString());
                    supervision.setSupervisonid(supervisionLog.getSupervisionlogid());
                    isTrue = supervisionDao.save(supervision);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return isTrue;
    }
    /**
     * 获取所有监理信息 用于国土局查看
     * @param proid
     * @return
     */
    @Override
    public List getFullLogInfo(String proid,String year,String month) {
        List result = new ArrayList();

       /* List condition = new ArrayList();
        condition.add("to_char(createtime,'yyyy-mm')='"+year.concat("-")+(Integer.valueOf(month) <10?"0"+month:month)+"'");*/
        //监理日志
        SupervisionLog supervisionLog = new SupervisionLog();
        supervisionLog.setProid(proid);


        List<SupervisionLog> lst = supervisionLogDao.find(supervisionLog);
        if(isNotNull(lst)&&lst.size()>0){
            for (SupervisionLog log : lst) {
                Map m = new HashMap();
                m.put("proid",log.getProid());
                m.put("start",log.getCreatetime());
                m.put("supervisionLogId",log.getSupervisionlogid());
                m.put("constructLogId","");
                m.put("title","监理日志"+Utils.getDateStr(log.getCreatetime()));
                result.add(m);
            }
        }
        ConstructLog constructLog= new ConstructLog();
        constructLog.setProid(proid);
       /* constructLog.setConditions(condition);*/
        List<ConstructLog> cLst = constructLogDao.find(constructLog);
        if(isNotNull(cLst)&&cLst.size()>0){
            for (ConstructLog log : cLst) {
                Map m = new HashMap();
                m.put("proid",log.getProid());
                m.put("start",log.getCreatetime());
                m.put("supervisionLogId","");
                m.put("constructLogId",log.getConstructlogid());
                m.put("title","施工日志"+Utils.getDateStr(log.getCreatetime()));
                result.add(m);
            }
        }
        return result;
    }

    /**
     * 获取项目监理日志详情（概要）
     * @param supervisionLog
     * @return
     */
    @Override
    public SupervisionLog getSupervisionLog(SupervisionLog supervisionLog) {
        List<SupervisionLog> lst = supervisionLogDao.find(supervisionLog);
        if(isNotNull(lst)&&lst.size()>0){
            return lst.get(0);
        }
        return null;
    }

    /**
     * 获取监理日志详细信息（详细）
     * @param supervision
     * @return
     */
    @Override
    public List<Supervision> getSupervisionDetail(Supervision supervision) {
        List<Supervision> lst = supervisionDao.find(supervision);
        return lst;
    }

    /**
     * 保存施工日志信息
     * @param constructLog
     * @return
     */
    @Override
    public boolean saveConstructLog(ConstructLog constructLog) {
        constructLog.setCreatetime(Utils.getStatesTime(0));
        constructLog.setConstructlogid(Utils.getRandomString());
        return constructLogDao.save(constructLog);
    }

    /**
     * 获取施工日志详细
     * @param constructLog
     * @return
     */
    @Override
    public ConstructLog getConstructDetail(ConstructLog constructLog) {
        List<ConstructLog> lst = constructLogDao.find(constructLog);
        if(isNotNull(lst)&&lst.size()>0){
            return lst.get(0);
        }
        return null;
    }


    /**
     * get file by fileName
     * @return
     */
    private File getNewFile(String localName,String path,String proId) {
        File file = null;
        try {

            path =path.concat(logPath.concat("\\origin\\"+proId));
            File folder = new File(path);
            if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
            file = new File(path.concat("/"+ Utils.getDatechar()+localName));
            if (file.exists()) {
                file.delete();
                //file = new File(path.concat("/" + reNameFile(name)));
            }
            file.createNewFile();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return file;
    }


    /**
     * 生成缩略图
     *
     * @param file
     */
    private void createThumb(File file) {

    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}

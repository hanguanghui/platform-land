package com.center.platform.controller;

import com.alibaba.fastjson.JSON;
import com.center.platform.entity.*;
import com.center.platform.service.ILogService;
import com.center.platform.service.IProjectService;
import com.center.platform.service.impl.LogServiceImpl;
import com.center.platform.utils.Utils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hangu on 2017/1/1.
 */
@Controller
@RequestMapping("/logService")
public class LogController extends BaseController {
    @Autowired
    private ILogService logService;
    @Autowired
    private IProjectService projectService;

    /**
     * 监理日志上传 页面跳转
     * @param proid
     * @param showUpload
     * @param model
     * @return
     */
    @RequestMapping("/uploadPrepare")
    public String uploadPepare(@RequestParam(value = "proid", required = true) String proid,
                               @RequestParam(value="showUpload",required = false,defaultValue = "false")boolean showUpload,
                               Model model) {

        Hproject pro= new Hproject();
        pro.setProid(proid);
        List<Hproject> lst = projectService.find(pro);
        if(lst.size()>0){
            pro = lst.get(0);
        }
        model.addAttribute("proid", proid);
        model.addAttribute("project", pro);
        model.addAttribute("showUpload",showUpload);
        return "/log/log";
    }

    /**
     * 施工日志页面
     * @param proid
     * @param model
     * @return
     */
    @RequestMapping("/constructLog")
    public String uploadPepare(@RequestParam(value = "proid", required = true) String proid,
                               Model model) {
        Hproject pro= new Hproject();
        pro.setProid(proid);
        List<Hproject> lst = projectService.find(pro);
        if(lst.size()>0){
            pro = lst.get(0);
        }
        model.addAttribute("proid", proid);
        model.addAttribute("project", pro);
        return "/log/constructLog";
    }

    /**
     * 保存施工日志
     * @param response
     * @param constructLog
     */
    @RequestMapping("/submitConLog")
    public void submitConLog(HttpServletResponse response,ConstructLog constructLog){
        result(logService.saveConstructLog(constructLog),response);
    }

    /**
     * 日历页面（施工+监理）
     * @param proid
     * @param model
     * @return
     */
    @RequestMapping("/calendarLog")
    public String calendarLog(@RequestParam(value = "proid", required = true) String proid,
                               Model model) {
        model.addAttribute("proid", proid);
        return "/log/calendarLog";
    }


    /**
     * 删除日志（图片日志）
     * @param logId
     * @param reponse
     */
    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam(value="logId",required = true)String logId,HttpServletResponse reponse){
        boolean istrue = logService.delete(logId);
        Map map= new HashMap();
        map.put("success",istrue);
        result(map,reponse);
    }

    /**
     * 监理日志、施工日志 上传文件（word doc docx）
     * 监理图片等
     * @param proid
     * @param request
     * @param session
     * @param response
     */
    @RequestMapping(value = "/upload", method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void upload(@RequestParam(value = "proid", required = true) String proid,
                       @RequestParam(value = "type", required = true) String type,
                       MultipartHttpServletRequest request,
                       HttpSession session,
                       HttpServletResponse response) {
        try {
            String Path = session.getServletContext().getRealPath("/").concat("\\static\\");
            Log log =logService.sava(request,proid,Path,type);
            result(JSON.toJSONString(log),response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 监理日志提交
     * @param request
     * @param response
     * @param session
     * @param supervisionLog
     * @param manager
     * @param artisan
     * @param other
     * @param materialone
     * @param materialtwo
     * @param materiathree
     * @param materiafour
     * @param machineone
     * @param machinetwo
     * @param machinethree
     * @param model
     */
    @RequestMapping("/submitSupLog")
    @ResponseBody
    public void submitSupLog(HttpServletRequest request,
                             HttpServletResponse response,
                             HttpSession session,
                             SupervisionLog supervisionLog,
                             @RequestParam(value="manager",required = true)String manager,
                             @RequestParam(value="artisan",required = true)String artisan,
                             @RequestParam(value="other",required = true)String other,
                             @RequestParam(value="materialone",required = true)String materialone,
                             @RequestParam(value="materialtwo",required = true)String materialtwo,
                             @RequestParam(value="materiathree",required = true)String materiathree,
                             @RequestParam(value="materiafour",required = true)String materiafour,
                             @RequestParam(value="machineone",required = true)String machineone,
                             @RequestParam(value="machinetwo",required = true)String machinetwo,
                             @RequestParam(value="machinethree",required = true)String machinethree,
                             Model model){
        if(supervisionLog!= null){
            List<Supervision> lst = new ArrayList<Supervision>();
            if(!("".equals(manager))){
                for(int i=0;i<manager.split(",").length;i++){
                    Supervision supervision = new Supervision();
                    supervision.setDetailid(Utils.getRandomString());
                    supervision.setManager(manager.split(",")[i]);
                    supervision.setArtisan(artisan.split(",")[i]);
                    supervision.setMachineone(machineone.split(",")[i]);
                    supervision.setMachinetwo(machinetwo.split(",")[i]);
                    supervision.setMachinethree(machinethree.split(",")[i]);
                    supervision.setMaterialone(materialone.split(",")[i]);
                    supervision.setMaterialtwo(materialtwo.split(",")[i]);
                    supervision.setMateriathree(materiathree.split(",")[i]);
                    supervision.setMateriafour(materiafour.split(",")[i]);
                    lst.add(supervision);
                }
                boolean istrue = logService.saveSupervisionLog(lst,supervisionLog);
                if(istrue){
                    Map m = new HashMap();
                    m.put("success",true);
                    result(m,response);
                }
            }
        }
    }

    @RequestMapping("/findYears")
    @ResponseBody
    public List<String> findYears(@RequestParam(value="proid",required = true)String proid){
        return logService.findYears(proid);
    }

    /**
     * 查询所有日志根据项目（监理 施工）
     * @param proid
     * @param month
     * @param year
     * @param response
     */
    @RequestMapping("/getFullLogInfo")
    @ResponseBody
    public void getFullLogInfo(@RequestParam(value = "proid",required = true)String proid,
                               @RequestParam(value="month",required = true)String month,
                               @RequestParam(value="year",required = true)String year,
                               HttpServletResponse response){
        result(logService.getFullLogInfo(proid,year,month),response);
    }

    /**
     * 请求监理日志详细信息
     * @param proid
     * @param supervisionLogId
     * @param model
     * @return
     */
    @RequestMapping("/supervisionDetail")
    public String supervisionDetail(@RequestParam(value = "proid",required = true)String proid,
                                    @RequestParam(value = "supervisionLogId",required = true)String supervisionLogId,
                                    Model model){
        SupervisionLog log = new SupervisionLog();
        log.setProid(proid);
        log.setSupervisionlogid(supervisionLogId);
        SupervisionLog supervisionLog = logService.getSupervisionLog(log);

        Supervision supervision = new Supervision();
        supervision.setSupervisonid(supervisionLogId);
        List<Supervision> lst = logService.getSupervisionDetail(supervision);
        model.addAttribute("supervisionLog",supervisionLog);
        model.addAttribute("supervisionLst",lst);
        model.addAttribute("proid",proid);
        return "/log/supervisionLogDetail";
    }


    /**
     * 施工日志详细
     * @param constructLogId
     * @param response
     */
    @RequestMapping("/constructLogDetail")
    public String constructLogDetail(@RequestParam(value = "constructLogId",required = true)String constructLogId,HttpServletResponse response,Model model){
        ConstructLog constructLog = new ConstructLog();
        constructLog.setConstructlogid(constructLogId);
        constructLog = logService.getConstructDetail(constructLog);
        model.addAttribute("constructLog",constructLog);
        return "/log/constructLogDetail";
    }

    /**
     *
     * @param
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object query(@RequestParam(value = "proid", required = true) String proid,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "12") int size,
                        @RequestParam(value = "type", defaultValue = "pic") String type,
                        @RequestParam(value = "year", defaultValue = "2017") String year,
                        @RequestParam(value = "month", defaultValue = "3") String month) {
        try {
           return this.logService.queryTasks(proid,year,type,month,page, size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 结果集合utf编码
     *
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

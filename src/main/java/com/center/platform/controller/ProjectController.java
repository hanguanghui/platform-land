package com.center.platform.controller;

import com.center.platform.entity.Company;
import com.center.platform.entity.Expert;
import com.center.platform.entity.Hproject;
import com.center.platform.entity.User;
import com.center.platform.service.IAssignmentService;
import com.center.platform.service.IProjectService;
import com.center.platform.utils.EnumUtils;
import com.center.platform.utils.EnumUtils.REWORD_TYPE;
import com.center.platform.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/project"})
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IAssignmentService assignmentService;

    /**
     * 拟建箱
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/draft")
    public String draft(HttpServletRequest request, Model model) {
        model.addAttribute("proid", Utils.getRandomString());
        model.addAttribute("dkid", Utils.getRandomString());
        return "/apply/draft";
    }

    /**
     * 创建项目
     *
     * @param request
     * @param session
     * @param project
     * @param company
     * @param expert
     * @param model
     * @return
     */
    @RequestMapping(value = "/draft/submit", method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String deaftSubmit(HttpServletRequest request, HttpSession session, Hproject project, Company company, Expert expert, Model model) {
        try {
            //设置初始变更状态
            project.setChanged("0");
            User user = (User) session.getAttribute("user");
            if ((project != null) && (user != null)) {
                List exps = new ArrayList();
                String[] expIds = expert.getExpertid().split(",");
                if ((isNotNull(expIds)) && (expIds.length > 0)) {
                    for (int i = 0; i < expIds.length; i++) {
                        Expert exp = new Expert();
                        exp.setExpertid(expIds[i]);
                        exp.setDepart(expert.getDepart().split(",")[i]);
                        exp.setExpertname(expert.getExpertname().split(",")[i]);
                        exp.setProfession(expert.getProfession().split(",")[i]);
                        exps.add(exp);
                    }
                }
                boolean b = this.projectService.save(project, user, exps, company);
                if (b) {
                    return "redirect:../undeal?taskstep=0";
                }
                return "error";
            }

            return "error";
        } catch (Exception ee) {
            ee.printStackTrace();
            throw new RuntimeException(ee);
        }
    }

    /**
     * 获取任务
     *
     * @param request
     * @param session
     * @param taskstep
     * @param model
     * @return
     */
    @RequestMapping("/undeal")
    public String undeal(HttpServletRequest request,
                         HttpSession session,
                         @RequestParam(value = "taskstep", required = true) String taskstep, Model model) {
        model.addAttribute("taskstep", taskstep);
        User user = (User) session.getAttribute("user");
        if (isNotNull(user)) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "用户名或密码无效！");
            return "redirect:../";
        }

        String userType = user.getType();
        if (userType.equalsIgnoreCase("0"))//专家
            return "verify/pro-list-expert";
        if (userType.equalsIgnoreCase("1")) //一般职能用户
            return "apply/pro-list";
        if (userType.equalsIgnoreCase("2")) //市局
            return "apply/pro-list-city";
        if (userType.equalsIgnoreCase("3")) { //编制单位
            return "verify/pro-list";
        }
        if (userType.equalsIgnoreCase("4")) { //监理单位
            return "verify/pro-list-supervisor";
        }
        if (userType.equalsIgnoreCase("5")) { //施工单位
            return "verify/pro-list-construct";
        }
        if (userType.equalsIgnoreCase("6")) { //管理员
            return "apply/pro-list";
        }
        return "apply/pro-list";
    }

    /**
     * 查询项目集合
     *
     * @param where
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object query(@RequestParam(value = "where", required = false, defaultValue = "1=1") String where,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            return this.projectService.queryTasks(where, page, size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据关系查询项目
     *
     * @param where
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/queryRelateInfo")
    @ResponseBody
    public Object queryRelateInfo(@RequestParam(value = "where", required = false, defaultValue = "1=1") String where,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            return this.projectService.queryRelationInfo(where, page, size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转发任务
     *
     * @param proid
     * @param limitDay
     * @param type
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/reword")
    @ResponseBody
    public Map reword(@RequestParam(value = "proid", required = true) String proid,
                      @RequestParam(value = "limitDay", required = false, defaultValue = "0") String limitDay,
                      @RequestParam(value = "type", required = false) String type,
                      HttpServletRequest request,
                      HttpSession session) {
        try {
            return this.projectService.rewordProject(proid, limitDay, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 县国土局上报项目  其他转发
     *
     * @param proid
     * @return
     */
    @RequestMapping("/report")
    @ResponseBody
    public Map report(@RequestParam(value = "proid", required = true) String proid) {
        Map map = this.projectService.rewordProject(proid, null, EnumUtils.REWORD_TYPE.report.name());
        return map;
    }

    /**
     * 市局分派评审专家
     *
     * @param experts
     * @param proid
     * @param limitTime
     * @return
     */
    @RequestMapping("/cityReword")
    @ResponseBody
    public Map cityReword(@RequestParam("experts") String experts,
                          @RequestParam("proid") String proid,
                          @RequestParam("limitTime") String limitTime) {
        String[] lst = experts.split(",");
        Map map = this.projectService.cityReword(lst, proid, limitTime);
        return map;
    }

    /**
     * 市局下发任务
     *
     * @param proid
     * @param constructId
     * @param supervisorId
     * @return
     */
    @RequestMapping("/approve")
    @ResponseBody
    public Map apporveCSCompany(@RequestParam(value = "proid", required = true) String proid,
                                @RequestParam(value = "constructId", required = true) String constructId,
                                @RequestParam(value = "supervisorId", required = true) String supervisorId) {
        Map map = this.projectService.approve(proid, constructId, supervisorId);
        return map;
    }

    @RequestMapping("/proDetail")
    public String proDetail(@RequestParam(value="proid",required=true)String proid,
                            Model model){
        if(isNotNull(proid)){
            Hproject pro = new Hproject();
            pro.setProid(proid);
            List<Hproject> lst = projectService.find(pro);
            if(isNotNull(lst)&&lst.size()>0){
                pro = lst.get(0);

                model.addAttribute("project",pro);
            }
        }
        return "/apply/proDetail";
    }
}
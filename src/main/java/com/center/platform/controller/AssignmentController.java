package com.center.platform.controller;

import com.center.platform.entity.User;
import com.center.platform.service.IAssignmentService;
import com.center.platform.service.IProjectService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/AssginmentService")
public class AssignmentController
{

    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    IProjectService projectService;

    /**
     * 编制单位等办结任务
     * @param proid
     * @param session
     * @return
     */
    @RequestMapping("/finishCompanyAssign")
    @ResponseBody
    public Map finishCompanyAssign(@RequestParam(value="proid", required=true) String proid, HttpSession session)
    {
        try
        {
            Map map = new HashMap();
            User user = (User)session.getAttribute("user");
            boolean istrue = this.assignmentService.finishCompanyAssign(proid, user);
            if (istrue)
                map.put("success", Boolean.valueOf(true));
            else {
                map.put("success", Boolean.valueOf(false));
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 评审专家等办结任务（同意或者不同意）
     * @param proid
     * @param agree
     * @param session
     * @return
     */
    @RequestMapping("/finishExpertAssign")
    @ResponseBody
    public Map finishExpertAssign(@RequestParam(value="proid", required=true) String proid, @RequestParam(value="agree", required=true) String agree, HttpSession session)
    {
        Map map = new HashMap();
        User user = (User)session.getAttribute("user");
        boolean istrue = this.assignmentService.finishExpertAssign(proid, user, Integer.valueOf(agree).intValue());
        if (istrue)
            map.put("success", Boolean.valueOf(true));
        else {
            map.put("success", Boolean.valueOf(false));
        }
        return map;
    }
}
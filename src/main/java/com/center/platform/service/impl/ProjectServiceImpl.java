package com.center.platform.service.impl;

import com.center.platform.dao.IHprojectDao;
import com.center.platform.entity.Assignment;
import com.center.platform.entity.Company;
import com.center.platform.entity.Expert;
import com.center.platform.entity.Hproject;
import com.center.platform.entity.Relation;
import com.center.platform.entity.User;
import com.center.platform.service.IAssignmentService;
import com.center.platform.service.ICompanyService;
import com.center.platform.service.IExpertService;
import com.center.platform.service.IProjectService;
import com.center.platform.service.IRelationService;
import com.center.platform.utils.EnumUtils;
import com.center.platform.utils.EnumUtils.NULL_TAG;
import com.center.platform.utils.EnumUtils.REWORD_TYPE;
import com.center.platform.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ProjectServiceImpl extends BaseLogger
        implements IProjectService
{

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IExpertService expertService;

    @Autowired
    private IHprojectDao projectDao;

    @Autowired
    private IRelationService relationService;

    @Autowired
    private ICompanyService companyService;

    @Override
    public List<Hproject> find(Hproject pro) {
        return projectDao.find(pro);
    }

    public boolean save(Hproject project, User user, List<Expert> lst, Company company)
    {
        Assert.notNull(project, EnumUtils.NULL_TAG.projectNull.name());
        Assert.notNull(user, EnumUtils.NULL_TAG.userNull.name());
        try
        {
            project.setProstep("0");
            project.setCreatetime(Utils.getStatesTime(0));

            String proid = project.getProid();

            boolean istrue = this.projectDao.save(project);
            if (istrue)
            {
                istrue = this.assignmentService.createAssignment(proid, user);
            }

            for (Expert expert : lst) {
                Expert exp = this.expertService.validExpert(expert);

                Relation relation = new Relation();
                relation.setCreatetime(Utils.getStatesTime(0));
                relation.setProjectId(proid);
                relation.setRelationId(Utils.getRandomString());
                relation.setRelationParentId(exp.getExpertid());
                relation.setState("0");

                istrue = this.relationService.save(relation);
            }

            company = this.companyService.validCompany(company, "b");

            Relation re = new Relation();
            re.setRelationId(Utils.getRandomString());
            re.setCreatetime(Utils.getStatesTime(0));
            re.setProjectId(proid);
            re.setRelationParentId(company.getCompanyid());
            re.setState("0");
            return this.relationService.save(re);
        }
        catch (Exception ee)
        {
            throw new RuntimeException(ee);
        }
    }

    public Page queryTasks(String where, int pageNum, int size)
    {
        try
        {
            List condition = Arrays.asList(where.split(","));
            Assignment assignment = new Assignment();
            assignment.setConditions(condition);

            List pros = this.projectDao.queryByAssign(assignment);
            int total = pros.size();
            int start = (pageNum - 1) * size;
            int end = pageNum * size > pros.size() ? pros.size() : pageNum * size;
            pros = pros.subList(start, end);

            return new PageImpl(pros, new PageRequest(pageNum, size), total);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Page queryRelationInfo(String where, int pageNum, int size)
    {
        try
        {
            List condition = Arrays.asList(where.split(","));
            Relation re = new Relation();
            re.setConditions(condition);

            List list = findProjectinfo(re);
            int total = list.size();
            int start = (pageNum - 1) * size;
            int end = pageNum * size > list.size() ? list.size() : pageNum * size;

            list = list.subList(start, end);

            return new PageImpl(list, new PageRequest(pageNum, size), total);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map rewordProject(String proid, String limitDay, String type)
    {
        try
        {
            Map resMap = new HashMap();
            boolean istrue = false;

            if (EnumUtils.REWORD_TYPE.report.name().equalsIgnoreCase(type)||isNull(type))
            {
                Assignment ass = new Assignment();
                ass.setProjectId(proid);
                List assLst = this.assignmentService.find(ass);

                if ((isNotNull(assLst)) && (assLst.size() > 0)) {
                    Assignment assignment = (Assignment)assLst.get(0);
                    assignment.setAssignmentDetailStep(String.valueOf(Integer.valueOf(assignment.getAssignmentDetailStep()).intValue() + 1));
                    istrue = this.assignmentService.update(assignment);
                }

                istrue = updateProjectStep(proid, 1);
            }
            else if (EnumUtils.REWORD_TYPE.company.name().equals(type))
            {
                List conditions = new ArrayList();
                conditions.add("limittime is null");
                Relation relation = new Relation();
                relation.setProjectId(proid);
                relation.setRelationId(proid);
                relation.setConditions(conditions);
                relation.setState("0");

                List lst = this.relationService.findByCompany(relation);
                if ((isNotNull(lst)) && (lst.size() > 0))
                {
                    Relation re = (Relation)lst.get(0);
                    re.setLimittime(limitDay);

                    istrue = this.relationService.update(re);
                    if (istrue) {
                        resMap.put("success", Boolean.valueOf(true));

                        Assignment ass = new Assignment();
                        ass.setProjectId(proid);
                        List assLst = this.assignmentService.find(ass);

                        if ((isNotNull(assLst)) && (assLst.size() > 0)) {
                            Assignment assignment = (Assignment)assLst.get(0);
                            assignment.setAssignmentDetailStep(String.valueOf(Integer.valueOf(assignment.getAssignmentDetailStep()).intValue() + 1));
                            istrue = this.assignmentService.update(assignment);
                        }

                        istrue = updateProjectStep(proid, 1);
                    }
                }
            } else if (!EnumUtils.REWORD_TYPE.expert.equals(type));
            if (istrue)
                resMap.put("success", Boolean.valueOf(true));
            else {
                resMap.put("success", Boolean.valueOf(false));
            }
            return resMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Hproject> findProjectinfo(Relation relation)
    {
        return this.projectDao.findProjectinfo(relation);
    }

    public boolean updateProjectStep(String proid, int step)
    {
        try {
            boolean istrue = false;
            Hproject pro = new Hproject();
            pro.setProid(proid);
            List lst = this.projectDao.find(pro);
            if ((isNotNull(lst)) && (lst.size() > 0)) {
                pro = (Hproject)lst.get(0);
                pro.setProstep(String.valueOf(Integer.valueOf(pro.getProstep()).intValue() + step));
            }return this.projectDao.update(pro);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Map cityReword(String[] exps, String proid, String limitTime)
    {
        try
        {
            Map map = new HashMap();
            boolean istrue = false;
            if ((isNotNull(exps)) && (isNotNull(proid))) {
                for (String id : exps)
                {
                    Relation re = new Relation();
                    re.setProjectId(proid);
                    re.setRelationId(Utils.getRandomString());
                    re.setState("0");
                    re.setCreatetime(Utils.getStatesTime(0));
                    re.setLimittime(limitTime);
                    re.setRelationParentId(id);

                    istrue = this.relationService.save(re);
                }

                Assignment ass = new Assignment();
                ass.setProjectId(proid);
                ass = this.assignmentService.rewordAssinStatus(ass, 1);

                istrue = updateProjectStep(proid, 1);
            }
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

    public Map approve(String proid, String constructId, String supervisorId)
    {
        try
        {
            Map map = new HashMap();
            boolean istrue = false;
            if ((isNotNull(proid)) && (isNotNull(constructId)) && (isNotNull(supervisorId)))
            {
                Hproject pro = new Hproject();
                pro.setProid(proid);
                List pros = this.projectDao.find(pro);
                if ((isNotNull(pros)) && (pros.size() > 0)) {
                    pro = (Hproject)pros.get(0);

                    Relation re = new Relation();
                    re.setState("0");
                    re.setCreatetime(Utils.getStatesTime(0));
                    re.setLimittime(String.valueOf(365.0D * pro.getProbuildtime()));
                    re.setProjectId(proid);
                    re.setRelationId(Utils.getRandomString());
                    re.setRelationParentId(constructId);
                    istrue = this.relationService.save(re);

                    re.setRelationId(Utils.getRandomString());
                    re.setRelationParentId(supervisorId);
                    istrue = this.relationService.save(re);

                    istrue = updateProjectStep(proid, 1);

                    Assignment assignment = new Assignment();
                    assignment.setProjectId(proid);
                    assignment = this.assignmentService.rewordAssinStatus(assignment, 1);
                    if (isNull(assignment)) {
                        istrue = true;
                    }
                }
            }
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
}
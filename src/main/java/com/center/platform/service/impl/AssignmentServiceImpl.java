package com.center.platform.service.impl;

import com.center.platform.dao.IAssignmentDao;
import com.center.platform.entity.Assignment;
import com.center.platform.entity.Relation;
import com.center.platform.entity.User;
import com.center.platform.service.IAssignmentService;
import com.center.platform.service.IFileService;
import com.center.platform.service.IProjectService;
import com.center.platform.service.IRelationService;
import com.center.platform.utils.EnumUtils;
import com.center.platform.utils.EnumUtils.NULL_TAG;
import com.center.platform.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AssignmentServiceImpl extends BaseLogger
        implements IAssignmentService
{

    @Autowired
    private IAssignmentDao assignmentDao;

    @Autowired
    private IFileService fileService;

    @Autowired
    IRelationService relationService;

    @Autowired
    IProjectService projectService;

    public boolean createAssignment(String proid, User user)
    {
        Assert.notNull(proid, EnumUtils.NULL_TAG.projectNull.name());
        Assert.notNull(user, EnumUtils.NULL_TAG.userNull.name());
        try {
            Assignment assignment = new Assignment();
            assignment.setCreatetime(Utils.getStatesTime(0));
            assignment.setAssignmentDetailStep("0");
            assignment.setAssignmentId(Utils.getRandomString());
            assignment.setAssignmentLock("0");
            assignment.setAssignmentName("申报");
            assignment.setAssignmentStep("0");
            assignment.setProjectId(proid);
            assignment.setUserId(user.getId());

            return this.assignmentDao.save(assignment);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Page queryTasks(String where, int pageNum, int size)
    {
        try
        {
            List condition = Arrays.asList(where.split(","));
            Assignment assignment = new Assignment();
            assignment.setConditions(condition);

            List assignments = find(assignment);
            int total = assignments.size();
            int start = (pageNum - 1) * size;
            int end = pageNum * size > assignments.size() ? assignments.size() : pageNum * size;
            assignments = assignments.subList(start, end);

            return new PageImpl(assignments, new PageRequest(pageNum, size), total);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assignment> find(Assignment assignment)
    {
        try {
            Assert.notNull(assignment);
            return this.assignmentDao.find(assignment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Assignment rewordAssinStatus(Assignment assignment, int step)
    {
        try {
            Assignment ass = new Assignment();
            List assLst = find(assignment);
            if ((isNotNull(assLst)) && (assLst.size() > 0)) {
                ass = (Assignment)assLst.get(0);
                ass.setAssignmentDetailStep(String.valueOf(Integer.valueOf(ass.getAssignmentDetailStep()).intValue() + step));
                boolean istrue = this.assignmentDao.update(ass);

                return ass;
            }

            return ass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Assignment assignment)
    {
        return this.assignmentDao.update(assignment);
    }

    public boolean finishCompanyAssign(String proid, User user)
    {
        try {
            if ((isNotNull(proid)) && (isNotNull(user))) {
                Assignment ass = new Assignment();
                ass.setProjectId(proid);

                ass = rewordAssinStatus(ass, 1);

                Relation relation = new Relation();
                relation.setProjectId(proid);
                relation.setRelationParentId(user.getId());
                List lst = this.relationService.find(relation);
                if ((isNotNull(lst)) && (lst.size() > 0)) {
                    relation = (Relation)lst.get(0);

                    relation.setState(String.valueOf(Integer.valueOf(relation.getState()).intValue() + 1));
                    boolean istrue = this.relationService.update(relation);

                    String condition = "limittime is null";
                    List conditions = new ArrayList();
                    conditions.add(condition);
                    Relation re = new Relation();
                    re.setProjectId(proid);
                    re.setConditions(conditions);
                    List<Relation> tmpLst = this.relationService.find(re);
                    if ((isNotNull(tmpLst)) && (tmpLst.size() > 0)) {
                        for (Relation relation1 : tmpLst) {
                            relation1.setLimittime(String.valueOf(Integer.valueOf(relation.getLimittime()).intValue() + Utils.getDifferDate(Utils.getStatesTime(0), relation.getCreatetime())));
                            istrue = this.relationService.update(relation1);
                        }
                    }

                    return this.projectService.updateProjectStep(proid, 1);
                }

                return false;
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean finishExpertAssign(String proid, User user, int agree)
    {
        try {
            boolean istrue = false;
            int res =1;
            if ((isNotNull(proid)) && (isNotNull(user)))
            {
                Relation relation = new Relation();
                relation.setProjectId(proid);
                relation.setRelationParentId(user.getId());

                List lst = this.relationService.find(relation);
                if ((isNotNull(lst)) && (lst.size() > 0)) {
                    relation = (Relation)lst.get(0);

                    relation.setState(String.valueOf(Integer.valueOf(relation.getState()).intValue() + agree));

                    istrue = this.relationService.update(relation);
                }

                res = this.relationService.validRelateProgress(proid);
                if (res != 0) {
                    Assignment ass = new Assignment();
                    ass.setProjectId(proid);
                    ass = rewordAssinStatus(ass, res);
                }
            }
            return this.projectService.updateProjectStep(proid, res);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean forwardAssign(String proid)
    {
        try {
            if (isNotNull(proid))
            {

                if (this.fileService.validFileSize(proid)) {
                    Assignment ass = new Assignment();
                    ass.setProjectId(proid);

                    ass = rewordAssinStatus(ass, 1);
                    if (isNotNull(ass)) {
                        return true;
                    }
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
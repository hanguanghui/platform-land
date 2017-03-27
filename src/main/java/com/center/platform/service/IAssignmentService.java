package com.center.platform.service;

import com.center.platform.entity.Assignment;
import com.center.platform.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface IAssignmentService
{
    /**
     * 创建任务
     * @param paramString
     * @param paramUser
     * @return
     */
    public abstract boolean createAssignment(String paramString, User paramUser);

    /**
     *
     * @param paramString
     * @param paramInt1
     * @param paramInt2
     * @return
     */
    public abstract Page queryTasks(String paramString, int paramInt1, int paramInt2);

    /**
     * 查询任务
     * @param paramAssignment
     * @return
     */
    public abstract List<Assignment> find(Assignment paramAssignment);

    /**
     * 根据参数 更新项目状态
     * @param paramAssignment
     * @param paramInt
     * @return
     */
    public abstract Assignment rewordAssinStatus(Assignment paramAssignment, int paramInt);

    /**
     * 更新任务状态
     * @param paramAssignment
     * @return
     */
    public abstract boolean update(Assignment paramAssignment);

    /**
     * 编制单位等办结任务（上传资料完成）
     * @param paramString
     * @param paramUser
     * @return
     */
    public abstract boolean finishCompanyAssign(String paramString, User paramUser);

    /**
     * 专家评审通过或不通过 办结任务
     * @param paramString
     * @param paramUser
     * @param paramInt
     * @return
     */
    public abstract boolean finishExpertAssign(String paramString, User paramUser, int paramInt);
}
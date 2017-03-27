package com.center.platform.entity;

import java.sql.Date;

public class Relation extends BaseEntity
{
    private String relationId;
    private String relationParentId;
    private String projectId;
    private Date createtime;
    private String limittime;
    private String state;
    private String opnion;

    public String getOpnion()
    {
        return this.opnion;
    }

    public void setOpnion(String opnion) {
        this.opnion = opnion;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRelationId() {
        return this.relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationParentId() {
        return this.relationParentId;
    }

    public void setRelationParentId(String relationParentId) {
        this.relationParentId = relationParentId;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getLimittime() {
        return this.limittime;
    }

    public void setLimittime(String limittime) {
        this.limittime = limittime;
    }
}
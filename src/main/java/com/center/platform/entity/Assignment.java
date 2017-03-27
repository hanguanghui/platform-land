package com.center.platform.entity;


import java.sql.Date;

public class Assignment extends BaseEntity {
    private String assignmentId;

    private String assignmentStep;

    private String assignmentName;

    private String projectId;

    private String userId;

    private String assignmentDetailStep;

    private String assignmentLock;

    private Date createtime;

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentStep() {
        return assignmentStep;
    }

    public void setAssignmentStep(String assignmentStep) {
        this.assignmentStep = assignmentStep;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssignmentDetailStep() {
        return assignmentDetailStep;
    }

    public void setAssignmentDetailStep(String assignmentDetailStep) {
        this.assignmentDetailStep = assignmentDetailStep;
    }

    public String getAssignmentLock() {
        return assignmentLock;
    }

    public void setAssignmentLock(String assignmentLock) {
        this.assignmentLock = assignmentLock;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
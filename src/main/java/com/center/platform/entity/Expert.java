package com.center.platform.entity;

public class Expert extends BaseEntity{
    private String expertid;

    private String expertname;

    private String profession;

    private String depart;

    private String rolevalue;

    public String getExpertid() {
        return expertid;
    }

    public void setExpertid(String expertid) {
        this.expertid = expertid;
    }

    public String getExpertname() {
        return expertname;
    }

    public void setExpertname(String expertname) {
        this.expertname = expertname;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getRolevalue() {
        return rolevalue;
    }

    public void setRolevalue(String rolevalue) {
        this.rolevalue = rolevalue;
    }
}
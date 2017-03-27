package com.center.platform.entity;

public class Company  extends BaseEntity{
    private String companyid;

    private String companyname;

    private String rolevalue;

    private String companytype;

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getRolevalue() {
        return rolevalue;
    }

    public void setRolevalue(String rolevalue) {
        this.rolevalue = rolevalue;
    }

    public String getCompanytype() {
        return companytype;
    }

    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }
}
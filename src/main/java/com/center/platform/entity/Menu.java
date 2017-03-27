package com.center.platform.entity;

public class Menu extends BaseEntity
{
    private String id;
    private String menuname;
    private String parentid;
    private String responseurl;
    private String rolevalue;
    private String icon;
    private String type;

    public String getType()
    {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuname() {
        return this.menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getResponseurl() {
        return this.responseurl;
    }

    public void setResponseurl(String responseurl) {
        this.responseurl = responseurl;
    }

    public String getRolevalue() {
        return this.rolevalue;
    }

    public void setRolevalue(String rolevalue) {
        this.rolevalue = rolevalue;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
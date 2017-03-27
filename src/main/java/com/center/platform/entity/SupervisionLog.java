package com.center.platform.entity;

import java.sql.Date;

public class SupervisionLog  extends BaseEntity{
    private String supervisionlogid;

    private String proid;

    private String proname;

    private String constructname;

    private String identifier;

    private String weather;

    private String temperature;

    private String power;

    private String direction;

    private String day;

    private Date createtime;

    private String engineer;

    private String chiefengineer;

    private String picturepath;

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getChiefengineer() {
        return chiefengineer;
    }

    public void setChiefengineer(String chiefengineer) {
        this.chiefengineer = chiefengineer;
    }

    public String getSupervisionlogid() {
        return supervisionlogid;
    }

    public void setSupervisionlogid(String supervisionlogid) {
        this.supervisionlogid = supervisionlogid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getConstructname() {
        return constructname;
    }

    public void setConstructname(String constructname) {
        this.constructname = constructname;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
package com.center.platform.entity;

import java.util.Date;

public class ConstructLog extends BaseEntity {
    private String constructlogid;

    private String proid;

    private Date createtime;

    private String day;

    private String weather;

    private String morning;

    private String afternoon;

    private String night;

    private String produceinfo;

    private String safetyinfomation;

    private String chief;

    private String notetaker;

    public String getConstructlogid() {
        return constructlogid;
    }

    public void setConstructlogid(String constructlogid) {
        this.constructlogid = constructlogid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getProduceinfo() {
        return produceinfo;
    }

    public void setProduceinfo(String produceinfo) {
        this.produceinfo = produceinfo;
    }

    public String getSafetyinfomation() {
        return safetyinfomation;
    }

    public void setSafetyinfomation(String safetyinfomation) {
        this.safetyinfomation = safetyinfomation;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
    }

    public String getNotetaker() {
        return notetaker;
    }

    public void setNotetaker(String notetaker) {
        this.notetaker = notetaker;
    }
}
package com.center.platform.entity;

import java.util.Date;

public class Material extends BaseEntity
{
    private String fileid;
    private String path;
    private Date createtime;
    private String proid;
    private double filesize;
    private String pdfpath;
    private String filename;

    public String getFileid()
    {
        return this.fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getProid() {
        return this.proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public double getFilesize() {
        return this.filesize;
    }

    public void setFilesize(double filesize) {
        this.filesize = filesize;
    }

    public String getPdfpath() {
        return this.pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
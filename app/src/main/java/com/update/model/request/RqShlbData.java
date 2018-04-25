package com.update.model.request;

import com.google.gson.annotations.SerializedName;

public class RqShlbData {


    /**
     * xz : 0
     * levels : 1
     * depid : 1
     * opid : 12
     * shzt : 0
     * shrq : null
     * opname : 管理员
     * depname : 总部
     * shrq1 : null
     * shyj : null
     * isok : null
     */

    @SerializedName("xz")
    private String xz;
    @SerializedName("levels")
    private int levels;
    @SerializedName("depid")
    private int depid;
    @SerializedName("opid")
    private int opid;
    @SerializedName("shzt")
    private int shzt;
    @SerializedName("shrq")
    private Object shrq;
    @SerializedName("opname")
    private String opname;
    @SerializedName("depname")
    private String depname;
    @SerializedName("shrq1")
    private Object shrq1;
    @SerializedName("shyj")
    private Object shyj;
    @SerializedName("isok")
    private Object isok;

    public String getXz() {
        return xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getDepid() {
        return depid;
    }

    public void setDepid(int depid) {
        this.depid = depid;
    }

    public int getOpid() {
        return opid;
    }

    public void setOpid(int opid) {
        this.opid = opid;
    }

    public int getShzt() {
        return shzt;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public Object getShrq() {
        return shrq;
    }

    public void setShrq(Object shrq) {
        this.shrq = shrq;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public Object getShrq1() {
        return shrq1;
    }

    public void setShrq1(Object shrq1) {
        this.shrq1 = shrq1;
    }

    public Object getShyj() {
        return shyj;
    }

    public void setShyj(Object shyj) {
        this.shyj = shyj;
    }

    public Object getIsok() {
        return isok;
    }

    public void setIsok(Object isok) {
        this.isok = isok;
    }
}

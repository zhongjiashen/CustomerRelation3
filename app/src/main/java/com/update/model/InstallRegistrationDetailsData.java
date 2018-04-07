package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/15 0015 下午 4:43
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/15 0015         申中佳               V1.0
 */
public class InstallRegistrationDetailsData {

    /**
     * installregid : 41
     * code : AZDJ-001-2018-03-14-00001
     * billdate : 2018-03-14
     * clientid : 55
     * cname : 巩义市瑞祥供水材料有限公司
     * lxrid : 3
     * lxrname : 王瑞祥
     * phone : 0371-7710129
     * shipto : 郑州高新区龙鼎工业园
     * projectid : null
     * projectname : null
     * bxr : 张三
     * bsrq : /Date(1520995159000+0800)/
     * sxfsid : 2
     * sxfsname : 上门安装
     * regtype : 1
     * regtypename : 复杂安装
     * priorid : 0
     * priorname : 普通
     * departmentid : 1
     * depname : 总部
     * empid : 48
     * empname : 张凯
     * memo : null
     * shzt : 0
     * opid : 5
     * opname : 管理员
     */

    @SerializedName("installregid")
    private int installregid;
    @SerializedName("code")
    private String code;
    @SerializedName("billdate")
    private String billdate;
    @SerializedName("clientid")
    private int clientid;
    @SerializedName("cname")
    private String cname;
    @SerializedName("lxrid")
    private int lxrid;
    @SerializedName("lxrname")
    private String lxrname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("shipto")
    private String shipto;
    @SerializedName("projectid")
    private int projectid;
    @SerializedName("projectname")
    private String projectname;
    @SerializedName("bxr")
    private String bxr;
    @SerializedName("bsrq")
    private String bsrq;
    @SerializedName("sxfsid")
    private String sxfsid;
    @SerializedName("sxfsname")
    private String sxfsname;
    @SerializedName("regtype")
    private int regtype;
    @SerializedName("regtypename")
    private String regtypename;
    @SerializedName("priorid")
    private int priorid;
    @SerializedName("priorname")
    private String priorname;
    @SerializedName("departmentid")
    private int departmentid;
    @SerializedName("depname")
    private String depname;
    @SerializedName("empid")
    private int empid;
    @SerializedName("empname")
    private String empname;
    @SerializedName("memo")
    private String memo;
    @SerializedName("shzt")
    private int shzt;
    @SerializedName("opid")
    private int opid;
    @SerializedName("opname")
    private String opname;

    public void setInstallregid(int installregid) {
        this.installregid = installregid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setLxrid(int lxrid) {
        this.lxrid = lxrid;
    }

    public void setLxrname(String lxrname) {
        this.lxrname = lxrname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public void setBxr(String bxr) {
        this.bxr = bxr;
    }

    public void setBsrq(String bsrq) {
        this.bsrq = bsrq;
    }

    public void setSxfsid(String sxfsid) {
        this.sxfsid = sxfsid;
    }

    public void setSxfsname(String sxfsname) {
        this.sxfsname = sxfsname;
    }

    public void setRegtype(int regtype) {
        this.regtype = regtype;
    }

    public void setRegtypename(String regtypename) {
        this.regtypename = regtypename;
    }

    public void setPriorid(int priorid) {
        this.priorid = priorid;
    }

    public void setPriorname(String priorname) {
        this.priorname = priorname;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public void setOpid(int opid) {
        this.opid = opid;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public int getInstallregid() {
        return installregid;
    }

    public String getCode() {
        return code;
    }

    public String getBilldate() {
        return billdate;
    }

    public int getClientid() {
        return clientid;
    }

    public String getCname() {
        return cname;
    }

    public int getLxrid() {
        return lxrid;
    }

    public String getLxrname() {
        return lxrname;
    }

    public String getPhone() {
        return phone;
    }

    public String getShipto() {
        return shipto;
    }

    public int getProjectid() {
        return projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public String getBxr() {
        return bxr;
    }

    public String getBsrq() {
        return bsrq;
    }

    public String getSxfsid() {
        return sxfsid;
    }

    public String getSxfsname() {
        return sxfsname;
    }

    public int getRegtype() {
        return regtype;
    }

    public String getRegtypename() {
        return regtypename;
    }

    public int getPriorid() {
        return priorid;
    }

    public String getPriorname() {
        return priorname;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public String getDepname() {
        return depname;
    }

    public int getEmpid() {
        return empid;
    }

    public String getEmpname() {
        return empname;
    }

    public String getMemo() {
        return memo;
    }

    public int getShzt() {
        return shzt;
    }

    public int getOpid() {
        return opid;
    }

    public String getOpname() {
        return opname;
    }
}

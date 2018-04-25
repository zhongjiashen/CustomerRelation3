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
     * serviceregid : 3
     * code : WXDJ-001-2018-04-20-00002
     * billdate : 2018-04-20
     * clientid : 136
     * cname : 晶佳科技
     * lxrid : 69
     * lxrname : 卫国
     * phone : 0378-93842249
     * shipto : 河南省郑州市上街区
     * projectid : null
     * projectname : null
     * bxr : 大法官
     * bsrq : 2018-04-20 09:04:44
     * sxfsid : 358
     * sxfsname : 上门服务
     * regtype : 0
     * regtypename : 派工
     * priorid : 0
     * priorname : 普通
     * djzt : 1
     * djztname : 未处理
     * departmentid : 1
     * depname : 总部
     * empid : 115
     * empname : 管理员
     * memo : null
     * shzt : 1
     * opid : 12
     * opname : 管理员
     */

    @SerializedName("serviceregid")
    private int serviceregid;
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
    private String projectid;
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
    @SerializedName("djzt")
    private int djzt;
    @SerializedName("djztname")
    private String djztname;
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

    public int getServiceregid() {
        return serviceregid;
    }

    public void setServiceregid(int serviceregid) {
        this.serviceregid = serviceregid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getLxrid() {
        return lxrid;
    }

    public void setLxrid(int lxrid) {
        this.lxrid = lxrid;
    }

    public String getLxrname() {
        return lxrname;
    }

    public void setLxrname(String lxrname) {
        this.lxrname = lxrname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getBxr() {
        return bxr;
    }

    public void setBxr(String bxr) {
        this.bxr = bxr;
    }

    public String getBsrq() {
        return bsrq;
    }

    public void setBsrq(String bsrq) {
        this.bsrq = bsrq;
    }

    public String getSxfsid() {
        return sxfsid;
    }

    public void setSxfsid(String sxfsid) {
        this.sxfsid = sxfsid;
    }

    public String getSxfsname() {
        return sxfsname;
    }

    public void setSxfsname(String sxfsname) {
        this.sxfsname = sxfsname;
    }

    public int getRegtype() {
        return regtype;
    }

    public void setRegtype(int regtype) {
        this.regtype = regtype;
    }

    public String getRegtypename() {
        return regtypename;
    }

    public void setRegtypename(String regtypename) {
        this.regtypename = regtypename;
    }

    public int getPriorid() {
        return priorid;
    }

    public void setPriorid(int priorid) {
        this.priorid = priorid;
    }

    public String getPriorname() {
        return priorname;
    }

    public void setPriorname(String priorname) {
        this.priorname = priorname;
    }

    public int getDjzt() {
        return djzt;
    }

    public void setDjzt(int djzt) {
        this.djzt = djzt;
    }

    public String getDjztname() {
        return djztname;
    }

    public void setDjztname(String djztname) {
        this.djztname = djztname;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getShzt() {
        return shzt;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public int getOpid() {
        return opid;
    }

    public void setOpid(int opid) {
        this.opid = opid;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }
}

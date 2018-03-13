package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/13 0013 下午 2:14
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/13 0013         申中佳               V1.0
 */
public class Master {
    @SerializedName("billid")
    private String billid;	//主单据ID
    @SerializedName("code")
    private String code;//单号
    @SerializedName("billdate")
    private String billdate;//生产日期
    @SerializedName("clientid")
    private String clientid;//客户ID
    @SerializedName("lxrid")
    private String lxrid;//联系人ID
    @SerializedName("lxrname")
    private String lxrname;//联系姓名
    @SerializedName("phone")
    private String phone;//电话
    @SerializedName("shipto")
    private String shipto;//联系地址
    @SerializedName("projectid")
    private String projectid;//项目ID
    @SerializedName("bxr")
    private String bxr;	//报送人
    @SerializedName("bsrq")
    private String bsrq;//报送日期
    @SerializedName("sxfsid")
    private String sxfsid;//服务方式ID
    @SerializedName("regtype")
    private String regtype;	//服务类型id
    @SerializedName("priorid")
    private String priorid;	//优先级ID
    @SerializedName("departmentid")
    private String departmentid;//部门id
    @SerializedName("empid")
    private String empid;//客户ID
    @SerializedName("memo")
    private String memo;//摘要
    @SerializedName("opid")
    private String opid;//操作员ID

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
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

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getLxrid() {
        return lxrid;
    }

    public void setLxrid(String lxrid) {
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

    public String getRegtype() {
        return regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype;
    }

    public String getPriorid() {
        return priorid;
    }

    public void setPriorid(String priorid) {
        this.priorid = priorid;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid;
    }
}

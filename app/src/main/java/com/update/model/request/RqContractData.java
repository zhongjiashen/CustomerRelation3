package com.update.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1363655717 on 2018-04-06.
 * 合同详情
 */

public class RqContractData {

    @SerializedName("contractid")
    private int contractid;
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
    @SerializedName("title")
    private String title;
    @SerializedName("qsrq")
    private String qsrq;
    @SerializedName("zzrq")
    private String zzrq;
    @SerializedName("amount")
    private double amount;
    @SerializedName("gmid")
    private Object gmid;
    @SerializedName("gmmc")
    private String gmmc;
    @SerializedName("chanceid")
    private int chanceid;
    @SerializedName("chancename")
    private String chancename;
    @SerializedName("projectid")
    private int projectid;
    @SerializedName("projectname")
    private String projectname;
    @SerializedName("departmentid")
    private int departmentid;
    @SerializedName("empid")
    private int empid;
    @SerializedName("depname")
    private String depname;
    @SerializedName("empname")
    private String empname;
    @SerializedName("memo")
    private String memo;

    public int getContractid() {
        return contractid;
    }

    public void setContractid(int contractid) {
        this.contractid = contractid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQsrq() {
        return qsrq;
    }

    public void setQsrq(String qsrq) {
        this.qsrq = qsrq;
    }

    public String getZzrq() {
        return zzrq;
    }

    public void setZzrq(String zzrq) {
        this.zzrq = zzrq;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Object getGmid() {
        return gmid;
    }

    public void setGmid(Object gmid) {
        this.gmid = gmid;
    }

    public String getGmmc() {
        return gmmc;
    }

    public void setGmmc(String gmmc) {
        this.gmmc = gmmc;
    }

    public int getChanceid() {
        return chanceid;
    }

    public void setChanceid(int chanceid) {
        this.chanceid = chanceid;
    }

    public String getChancename() {
        return chancename;
    }

    public void setChancename(String chancename) {
        this.chancename = chancename;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
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
}

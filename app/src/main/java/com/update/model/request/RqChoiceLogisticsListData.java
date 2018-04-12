package com.update.model.request;

/**
 * Created by 1363655717 on 2018-04-10.
 *
 * 物流单引用单据
 */

public class RqChoiceLogisticsListData {


    /**
     * billid : 2
     * code : CGSH-001-2018-04-09-00001
     * billdate : 2018-04-09
     * billtypeid : 7
     * billtypename : 采购收货单
     * Shipclientid : 169
     * Shipcname : 昊
     * totalamt : 30000.0
     * lxrid : null
     * lxrname : null
     * phone : 0371-53767758
     * projectid : null
     * projectname : null
     * shipto : null
     * bankid : null
     * bankname : null
     * proxyamt : 0.0
     * isproxy : 0
     */

    private int billid;
    private String code;
    private String billdate;
    private int billtypeid;
    private String billtypename;
    private int Shipclientid;
    private String Shipcname;
    private double totalamt;
    private Object lxrid;
    private Object lxrname;
    private String phone;
    private Object projectid;
    private Object projectname;
    private Object shipto;
    private Object bankid;
    private Object bankname;
    private double proxyamt;
    private int isproxy;

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
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

    public int getBilltypeid() {
        return billtypeid;
    }

    public void setBilltypeid(int billtypeid) {
        this.billtypeid = billtypeid;
    }

    public String getBilltypename() {
        return billtypename;
    }

    public void setBilltypename(String billtypename) {
        this.billtypename = billtypename;
    }

    public int getShipclientid() {
        return Shipclientid;
    }

    public void setShipclientid(int Shipclientid) {
        this.Shipclientid = Shipclientid;
    }

    public String getShipcname() {
        return Shipcname;
    }

    public void setShipcname(String Shipcname) {
        this.Shipcname = Shipcname;
    }

    public double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(double totalamt) {
        this.totalamt = totalamt;
    }

    public Object getLxrid() {
        return lxrid;
    }

    public void setLxrid(Object lxrid) {
        this.lxrid = lxrid;
    }

    public Object getLxrname() {
        return lxrname;
    }

    public void setLxrname(Object lxrname) {
        this.lxrname = lxrname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getProjectid() {
        return projectid;
    }

    public void setProjectid(Object projectid) {
        this.projectid = projectid;
    }

    public Object getProjectname() {
        return projectname;
    }

    public void setProjectname(Object projectname) {
        this.projectname = projectname;
    }

    public Object getShipto() {
        return shipto;
    }

    public void setShipto(Object shipto) {
        this.shipto = shipto;
    }

    public Object getBankid() {
        return bankid;
    }

    public void setBankid(Object bankid) {
        this.bankid = bankid;
    }

    public Object getBankname() {
        return bankname;
    }

    public void setBankname(Object bankname) {
        this.bankname = bankname;
    }

    public double getProxyamt() {
        return proxyamt;
    }

    public void setProxyamt(double proxyamt) {
        this.proxyamt = proxyamt;
    }

    public int getIsproxy() {
        return isproxy;
    }

    public void setIsproxy(int isproxy) {
        this.isproxy = isproxy;
    }
}

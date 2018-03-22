package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/22 0022 下午 3:05
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/22 0022         申中佳               V1.0
 */
public class InstallationDetailsData {

    /**
     * billid : 5
     * itemno : 19
     * lb : 1
     * cname : 河南省中显光电科技有限公司
     * lxrname : 郭志杰
     * phone : 0378-93842257
     * shipto : 河南省郑州市中原区
     * bsrq : 2018-03-22 09:34:44
     * empnames : 管理员,张凯
     * code : AZPG-001-2018-03-22-00002
     * billdate : 2018-03-22
     * zxjg : [未执行]
     * goodsid : 7
     * goodscode : 12063
     * goodsname : 摄像机
     * specs : null
     * model : JE-211
     * unitname : 台
     * unitqty : 1.0
     * wxjgid : 1
     * wxjgname : 待安装
     * isreturn : 0
     * yesqty : null
     * noqty : 1.0
     * begindate : null
     * enddate : null
     * planinfo : null
     * serialinfo : null
     * installregid : 70
     * jobshzt : 1
     */

    @SerializedName("billid")
    private int billid;
    @SerializedName("itemno")
    private int itemno;
    @SerializedName("lb")
    private int lb;
    @SerializedName("cname")
    private String cname;
    @SerializedName("lxrname")
    private String lxrname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("shipto")
    private String shipto;
    @SerializedName("bsrq")
    private String bsrq;
    @SerializedName("empnames")
    private String empnames;
    @SerializedName("code")
    private String code;
    @SerializedName("billdate")
    private String billdate;
    @SerializedName("zxjg")
    private String zxjg;
    @SerializedName("goodsid")
    private int goodsid;
    @SerializedName("goodscode")
    private String goodscode;
    @SerializedName("goodsname")
    private String goodsname;
    @SerializedName("specs")
    private Object specs;
    @SerializedName("model")
    private String model;
    @SerializedName("unitname")
    private String unitname;
    @SerializedName("unitqty")
    private double unitqty;
    @SerializedName("wxjgid")
    private int wxjgid;
    @SerializedName("wxjgname")
    private String wxjgname;
    @SerializedName("isreturn")
    private int isreturn;
    @SerializedName("yesqty")
    private Object yesqty;
    @SerializedName("noqty")
    private double noqty;
    @SerializedName("begindate")
    private Object begindate;
    @SerializedName("enddate")
    private Object enddate;
    @SerializedName("planinfo")
    private Object planinfo;
    @SerializedName("serialinfo")
    private Object serialinfo;
    @SerializedName("installregid")
    private int installregid;
    @SerializedName("jobshzt")
    private int jobshzt;

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public int getItemno() {
        return itemno;
    }

    public void setItemno(int itemno) {
        this.itemno = itemno;
    }

    public int getLb() {
        return lb;
    }

    public void setLb(int lb) {
        this.lb = lb;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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

    public String getBsrq() {
        return bsrq;
    }

    public void setBsrq(String bsrq) {
        this.bsrq = bsrq;
    }

    public String getEmpnames() {
        return empnames;
    }

    public void setEmpnames(String empnames) {
        this.empnames = empnames;
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

    public String getZxjg() {
        return zxjg;
    }

    public void setZxjg(String zxjg) {
        this.zxjg = zxjg;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public Object getSpecs() {
        return specs;
    }

    public void setSpecs(Object specs) {
        this.specs = specs;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public double getUnitqty() {
        return unitqty;
    }

    public void setUnitqty(double unitqty) {
        this.unitqty = unitqty;
    }

    public int getWxjgid() {
        return wxjgid;
    }

    public void setWxjgid(int wxjgid) {
        this.wxjgid = wxjgid;
    }

    public String getWxjgname() {
        return wxjgname;
    }

    public void setWxjgname(String wxjgname) {
        this.wxjgname = wxjgname;
    }

    public int getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(int isreturn) {
        this.isreturn = isreturn;
    }

    public Object getYesqty() {
        return yesqty;
    }

    public void setYesqty(Object yesqty) {
        this.yesqty = yesqty;
    }

    public double getNoqty() {
        return noqty;
    }

    public void setNoqty(double noqty) {
        this.noqty = noqty;
    }

    public Object getBegindate() {
        return begindate;
    }

    public void setBegindate(Object begindate) {
        this.begindate = begindate;
    }

    public Object getEnddate() {
        return enddate;
    }

    public void setEnddate(Object enddate) {
        this.enddate = enddate;
    }

    public Object getPlaninfo() {
        return planinfo;
    }

    public void setPlaninfo(Object planinfo) {
        this.planinfo = planinfo;
    }

    public Object getSerialinfo() {
        return serialinfo;
    }

    public void setSerialinfo(Object serialinfo) {
        this.serialinfo = serialinfo;
    }

    public int getInstallregid() {
        return installregid;
    }

    public void setInstallregid(int installregid) {
        this.installregid = installregid;
    }

    public int getJobshzt() {
        return jobshzt;
    }

    public void setJobshzt(int jobshzt) {
        this.jobshzt = jobshzt;
    }
}

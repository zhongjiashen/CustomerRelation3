package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/6 0006 下午 5:18
 * Description:选择商品或增加概况的信息
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/6 0006         申中佳               V1.0
 */
public class GoodsOrOverviewData {
    @SerializedName("billid")
    private String billid;	//主单据ID
    @SerializedName("itemno")
    private String itemno;	//明细ID
    @SerializedName("lb")
    private String lb;	//概况=0，商品=1
    @SerializedName("goodsid")
    private String goodsid;	//商品ID  如果lb=0 则为0
    @SerializedName("goodsname")
    private String goodsname;	//商品名称或概况内容
    @SerializedName("unitid")
    private String unitid;	//计量单位ID
    @SerializedName("unitqty")
    private String unitqty;	//数量
    @SerializedName("serialinfo")
    private String serialinfo;//序列号GUID
    @SerializedName("ensureid")
    private String ensureid;// 保修状态ID
    @SerializedName("ensurename")
    private String ensurename;// 保修状态名称
    @SerializedName("faultid")
    private String faultid;// 故障类别ID
    @SerializedName("faultname")
    private String faultname;// 故障类别名称
    @SerializedName("faultinfo")
    private String faultinfo;//故障描述

    public String getEnsurename() {
        return ensurename;
    }

    public void setEnsurename(String ensurename) {
        this.ensurename = ensurename;
    }

    public String getFaultname() {
        return faultname;
    }

    public void setFaultname(String faultname) {
        this.faultname = faultname;
    }

    public String getEnsureid() {
        return ensureid;
    }

    public void setEnsureid(String ensureid) {
        this.ensureid = ensureid;
    }

    public String getFaultid() {
        return faultid;
    }

    public void setFaultid(String faultid) {
        this.faultid = faultid;
    }

    public String getFaultinfo() {
        return faultinfo;
    }

    public void setFaultinfo(String faultinfo) {
        this.faultinfo = faultinfo;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitqty() {
        return unitqty;
    }

    public void setUnitqty(String unitqty) {
        this.unitqty = unitqty;
    }

    public String getSerialinfo() {
        return serialinfo;
    }

    public void setSerialinfo(String serialinfo) {
        this.serialinfo = serialinfo;
    }
}

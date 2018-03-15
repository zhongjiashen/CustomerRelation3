package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/6 0006 下午 5:18
 * Description:商品或增加概况的信息
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/6 0006         申中佳               V1.0
 */
public class InstallRegistrationScheduleData {

    /**
     * installregid : 41
     * itemno : 57
     * lb : 1
     * goodsid : 7
     * goodscode : 12063
     * goodsname : 摄像机
     * specs : null
     * model : JE-211
     * unitid : 19
     * unitname : 台
     * unitqty : 1.0
     * serialinfo : 06E75337-16B4-4A32-AC64-CDCA34996796
     */

    @SerializedName("installregid")
    private int installregid;
    @SerializedName("itemno")
    private int itemno;
    @SerializedName("lb")
    private int lb;
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
    @SerializedName("unitid")
    private int unitid;
    @SerializedName("unitname")
    private String unitname;
    @SerializedName("unitqty")
    private double unitqty;
    @SerializedName("serialinfo")
    private String serialinfo;

    public void setInstallregid(int installregid) {
        this.installregid = installregid;
    }

    public void setItemno(int itemno) {
        this.itemno = itemno;
    }

    public void setLb(int lb) {
        this.lb = lb;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public void setSpecs(Object specs) {
        this.specs = specs;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public void setUnitqty(double unitqty) {
        this.unitqty = unitqty;
    }

    public void setSerialinfo(String serialinfo) {
        this.serialinfo = serialinfo;
    }

    public int getInstallregid() {
        return installregid;
    }

    public int getItemno() {
        return itemno;
    }

    public int getLb() {
        return lb;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public Object getSpecs() {
        return specs;
    }

    public String getModel() {
        return model;
    }

    public int getUnitid() {
        return unitid;
    }

    public String getUnitname() {
        return unitname;
    }

    public double getUnitqty() {
        return unitqty;
    }

    public String getSerialinfo() {
        return serialinfo;
    }
}

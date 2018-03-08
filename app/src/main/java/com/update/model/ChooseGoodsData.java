package com.update.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/7 0007 下午 5:29
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/7 0007         申中佳               V1.0
 */
public class ChooseGoodsData {

    /**
     * goodsid : 7
     * code : 12063
     * name : 摄像机
     * specs : null
     * model : JE-211
     * inf_costingtypeid : 1
     * goodstypeid : 66
     * goodstypename : 监控系列产品
     * onhand : 379.0
     * aprice : 1000.0
     * unitid : 19
     * unitname : 台
     * batchctrl : F
     * serialctrl : F
     */

    @SerializedName("goodsid")
    private int goodsid;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("specs")
    private Object specs;
    @SerializedName("model")
    private String model;
    @SerializedName("inf_costingtypeid")
    private int infCostingtypeid;
    @SerializedName("goodstypeid")
    private int goodstypeid;
    @SerializedName("goodstypename")
    private String goodstypename;
    @SerializedName("onhand")
    private double onhand;
    @SerializedName("aprice")
    private double aprice;
    @SerializedName("unitid")
    private int unitid;
    @SerializedName("unitname")
    private String unitname;
    @SerializedName("batchctrl")
    private String batchctrl;
    @SerializedName("serialctrl")
    private String serialctrl;
    /**
     * 是否被选中
     */
    private boolean check;
    private double number=1.0;//数量
    private String serialinfo;//序列号
    private List<Serial> mSerials;//序列号临时保存集合

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getSerialinfo() {
        return serialinfo;
    }

    public void setSerialinfo(String serialinfo) {
        this.serialinfo = serialinfo;
    }

    public List<Serial> getSerials() {
        return mSerials;
    }

    public void setSerials(List<Serial> serials) {
        mSerials = serials;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getInfCostingtypeid() {
        return infCostingtypeid;
    }

    public void setInfCostingtypeid(int infCostingtypeid) {
        this.infCostingtypeid = infCostingtypeid;
    }

    public int getGoodstypeid() {
        return goodstypeid;
    }

    public void setGoodstypeid(int goodstypeid) {
        this.goodstypeid = goodstypeid;
    }

    public String getGoodstypename() {
        return goodstypename;
    }

    public void setGoodstypename(String goodstypename) {
        this.goodstypename = goodstypename;
    }

    public double getOnhand() {
        return onhand;
    }

    public void setOnhand(double onhand) {
        this.onhand = onhand;
    }

    public double getAprice() {
        return aprice;
    }

    public void setAprice(double aprice) {
        this.aprice = aprice;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getBatchctrl() {
        return batchctrl;
    }

    public void setBatchctrl(String batchctrl) {
        this.batchctrl = batchctrl;
    }

    public String getSerialctrl() {
        return serialctrl;
    }

    public void setSerialctrl(String serialctrl) {
        this.serialctrl = serialctrl;
    }
}

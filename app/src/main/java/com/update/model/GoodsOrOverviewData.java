package com.update.model;

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
    private String billid ;//单据ID
    private String itemno;//明细ID
    private String lb ;// 概况=0，商品=1
    private String goodsid;//商品ID  如果lb=0 则为0
    private String goodsname;// 商品名称或概况内容
    private String unitid;//计量单位ID
    private String unitqty;//数量
    private String serialinfo;// 序列号GUID

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

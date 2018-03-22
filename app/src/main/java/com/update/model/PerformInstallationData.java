package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/22 0022 上午 10:18
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/22 0022         申中佳               V1.0
 */
public class PerformInstallationData {

    /**
     * billid : 5
     * itemno : 19
     * code : AZPG-001-2018-03-22-00002
     * billdate : 2018-03-22
     * clientid : 76
     * cname : 河南省中显光电科技有限公司
     * lb : 1
     * goodscode : 12063
     * goodsname : 摄像机
     * empnames : 管理员,张凯
     * shzt : 0
     */

    @SerializedName("billid")
    private int billid;
    @SerializedName("itemno")
    private int itemno;
    @SerializedName("code")
    private String code;
    @SerializedName("billdate")
    private String billdate;
    @SerializedName("clientid")
    private int clientid;
    @SerializedName("cname")
    private String cname;
    @SerializedName("lb")
    private int lb;
    @SerializedName("goodscode")
    private String goodscode;
    @SerializedName("goodsname")
    private String goodsname;
    @SerializedName("empnames")
    private String empnames;
    @SerializedName("shzt")
    private int shzt;
    /**
     * wxjgid : 3
     * wxjgname : 完成
     */

    @SerializedName("wxjgid")
    private int wxjgid;
    @SerializedName("wxjgname")
    private String wxjgname;


    public void setBillid(int billid) {
        this.billid = billid;
    }

    public void setItemno(int itemno) {
        this.itemno = itemno;
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

    public void setLb(int lb) {
        this.lb = lb;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public void setEmpnames(String empnames) {
        this.empnames = empnames;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public int getBillid() {
        return billid;
    }

    public int getItemno() {
        return itemno;
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

    public int getLb() {
        return lb;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public String getEmpnames() {
        return empnames;
    }

    public int getShzt() {
        return shzt;
    }

    public void setWxjgid(int wxjgid) {
        this.wxjgid = wxjgid;
    }

    public void setWxjgname(String wxjgname) {
        this.wxjgname = wxjgname;
    }

    public int getWxjgid() {
        return wxjgid;
    }

    public String getWxjgname() {
        return wxjgname;
    }
}

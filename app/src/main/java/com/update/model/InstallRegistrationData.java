package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/15 0015 上午 9:17
 * Description:安装登记列表
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/15 0015         申中佳               V1.0
 */
public class InstallRegistrationData {

    /**
     * billid : 41
     * code : AZDJ-001-2018-03-14-00001
     * billdate : 2018-03-14
     * clientid : 55
     * cname : 巩义市瑞祥供水材料有限公司
     * amount : 0.0
     * shzt : 0
     * djzt : 1
     * djztname : 未处理
     */

    @SerializedName("billid")
    private int billid;//单据ID
    @SerializedName("code")
    private String code;// 单据编号
    @SerializedName("billdate")
    private String billdate;//单据日期
    @SerializedName("clientid")
    private int clientid;//客户ID
    @SerializedName("cname")
    private String cname;//客户名称
    @SerializedName("amount")
    private double amount;// 总金额 (机会单是预计收入)
    @SerializedName("shzt")
    private int shzt;//审核状态(0未审 1已审 2 审核中)
    @SerializedName("djzt")
    private int djzt;//登记状态ID
    @SerializedName("djztname")
    private String djztname;//登记状态名称

    public void setBillid(int billid) {
        this.billid = billid;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public void setDjzt(int djzt) {
        this.djzt = djzt;
    }

    public void setDjztname(String djztname) {
        this.djztname = djztname;
    }

    public int getBillid() {
        return billid;
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

    public double getAmount() {
        return amount;
    }

    public int getShzt() {
        return shzt;
    }

    public int getDjzt() {
        return djzt;
    }

    public String getDjztname() {
        return djztname;
    }
}

package com.update.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1363655717 on 2018-04-05.
 */

public class RqProjectListData {

    @SerializedName("billid")
    private int billid;
    @SerializedName("code")
    private String code;
    @SerializedName("billdate")
    private String billdate;
    @SerializedName("clientid")
    private int clientid;
    @SerializedName("cname")
    private String cname;
    @SerializedName("amount")
    private double amount;
    @SerializedName("shzt")
    private int shzt;
    @SerializedName("title")
    private String title;
    @SerializedName("gmid")
    private String gmid;
    @SerializedName("gmmc")
    private String gmmc;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShzt() {
        return shzt;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGmid() {
        return gmid;
    }

    public void setGmid(String gmid) {
        this.gmid = gmid;
    }

    public String getGmmc() {
        return gmmc;
    }

    public void setGmmc(String gmmc) {
        this.gmmc = gmmc;
    }
}

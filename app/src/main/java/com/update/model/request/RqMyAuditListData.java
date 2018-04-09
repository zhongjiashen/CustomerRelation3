package com.update.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1363655717 on 2018-04-08.
 */

public class RqMyAuditListData {

    @SerializedName("billid")
    private int billid;
    @SerializedName("parms")
    private String parms;
    @SerializedName("billtypename")
    private String billtypename;
    @SerializedName("code")
    private String code;
    @SerializedName("billdate")
    private String billdate;
    @SerializedName("opname")
    private String opname;
    @SerializedName("cname")
    private String cname;
    @SerializedName("amount")
    private Object amount;
    @SerializedName("shzt")
    private int shzt;

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public String getParms() {
        return parms;
    }

    public void setParms(String parms) {
        this.parms = parms;
    }

    public String getBilltypename() {
        return billtypename;
    }

    public void setBilltypename(String billtypename) {
        this.billtypename = billtypename;
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

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public int getShzt() {
        return shzt;
    }

    public void setShzt(int shzt) {
        this.shzt = shzt;
    }
}

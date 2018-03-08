package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/8 0008 下午 2:39
 * Description:序列号
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/8 0008         申中佳               V1.0
 */
public class Serial {
    @SerializedName("billid")
    private String billid;	//主单据ID
    @SerializedName("serialinfo")
    private String serialinfo;	//序列号GUID
    @SerializedName("serno")
    private String serno;	//序列号码

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getSerialinfo() {
        return serialinfo;
    }

    public void setSerialinfo(String serialinfo) {
        this.serialinfo = serialinfo;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }
}

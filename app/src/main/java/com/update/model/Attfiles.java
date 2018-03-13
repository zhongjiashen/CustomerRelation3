package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/13 0013 下午 2:06
 * Description:文件保存
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/13 0013         申中佳               V1.0
 */
public class Attfiles {
    @SerializedName("billid")
    private String billid;	//主单据ID
    @SerializedName("clientid")
    private String clientid;	//客户ID
    @SerializedName("filenames")
    private String filenames;	//文件名称
    @SerializedName("opid")
    private String opid;	//操作员ID
    @SerializedName("xx")
    private String xx;	//二进制数据编码为BASE64字符串

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getFilenames() {
        return filenames;
    }

    public void setFilenames(String filenames) {
        this.filenames = filenames;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }
}

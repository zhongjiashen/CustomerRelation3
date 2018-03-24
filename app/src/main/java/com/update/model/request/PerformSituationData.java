package com.update.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/24 0024 下午 1:49
 * Description:执行情况保存信息提交实体类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/24 0024         申中佳               V1.0
 */
public class PerformSituationData {
  /*
    Billid  主单ID                            Billid    主单ID
    itemno  明细ID                            itemno  明细ID
    wxjgid  安装结果ID                        wxjgid  维修结果ID
    isreturn 重新派工 0否 1是                 isreturn 重新派工 0否 1是
    yesqty  已装数量                          yesqty  已装数量
    noqty   未装数量                          noqty   未装数量
                                              desqty  报废数量
    begindate 开始时间                        begindate 开始时间
    enddate  结束时间                         enddate  结束时间
                                              factfault  实际故障
    planinfo  安装措施                        planinfo  维修措施
    serialinfo  序列号GUID                    serialinfo  序列号GUID
    opid      操作员ID                        opid      操作员ID
    */

    @SerializedName("billid")
    private String billid;	//主单据ID
    @SerializedName("itemno")
    private String itemno;	//明细ID
    @SerializedName("wxjgid")
    private String wxjgid;//  维修结果ID
    @SerializedName("isreturn")
    private String isreturn;// 重新派工 0否 1是
    @SerializedName("yesqty")
    private String yesqty;//  已装数量
    @SerializedName("noqty")
    private String noqty;//   未装数量
    @SerializedName("desqty")
    private String desqty;//  报废数量
    @SerializedName("begindate")
    private String begindate;// 开始时间
    @SerializedName("enddate")
    private String enddate;//  结束时间
    @SerializedName("factfault")
    private String factfault;//  实际故障
    @SerializedName("planinfo")
    private String planinfo ;// 维修措施
    @SerializedName("serialinfo")
    private String serialinfo;//  序列号GUID
    @SerializedName("opid")
    private String opid  ;//    操作员ID*/*/

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

    public String getWxjgid() {
        return wxjgid;
    }

    public void setWxjgid(String wxjgid) {
        this.wxjgid = wxjgid;
    }

    public String getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(String isreturn) {
        this.isreturn = isreturn;
    }

    public String getYesqty() {
        return yesqty;
    }

    public void setYesqty(String yesqty) {
        this.yesqty = yesqty;
    }

    public String getNoqty() {
        return noqty;
    }

    public void setNoqty(String noqty) {
        this.noqty = noqty;
    }

    public String getDesqty() {
        return desqty;
    }

    public void setDesqty(String desqty) {
        this.desqty = desqty;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getFactfault() {
        return factfault;
    }

    public void setFactfault(String factfault) {
        this.factfault = factfault;
    }

    public String getPlaninfo() {
        return planinfo;
    }

    public void setPlaninfo(String planinfo) {
        this.planinfo = planinfo;
    }

    public String getSerialinfo() {
        return serialinfo;
    }

    public void setSerialinfo(String serialinfo) {
        this.serialinfo = serialinfo;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid;
    }
}

package com.update.model.request;

/**
 * Created by 1363655717 on 2018-04-10.
 */

public class RqLogisticsListData {


    /**
     * billid : 1
     * code : WL-001-2018-04-08-00001
     * billdate : 2018-04-08
     * clientid : 177
     * cname : 圆通快递
     * amount : 0.0
     * shzt : 0
     * shipcname : 圆通快递
     */

    private int billid;
    private String code;
    private String billdate;
    private int clientid;
    private String cname;
    private double amount;
    private int shzt;
    private String shipcname;

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

    public String getShipcname() {
        return shipcname;
    }

    public void setShipcname(String shipcname) {
        this.shipcname = shipcname;
    }
}

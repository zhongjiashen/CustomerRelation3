package com.cr.data;

public class BaseResponseData {


    /**
     * status : yes
     * msg : 查询成功
     * ipaddress : 39.105.231.17
     * webuserid : 1
     * verregids : 2
     */

    private String status;
    private String msg;
    private String ipaddress;
    private String webuserid;
    private String verregids;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getWebuserid() {
        return webuserid;
    }

    public void setWebuserid(String webuserid) {
        this.webuserid = webuserid;
    }

    public String getVerregids() {
        return verregids;
    }

    public void setVerregids(String verregids) {
        this.verregids = verregids;
    }
}

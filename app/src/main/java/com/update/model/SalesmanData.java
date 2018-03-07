package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/7 0007 上午 11:46
 * Description:业务员信息实体类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/7 0007         申中佳               V1.0
 */
public class SalesmanData {

    /**
     * id : 47
     * empcode : ZG0001
     * empname : 管理员
     * departmentid : 1
     * depname : 总部
     */

    @SerializedName("id")
    private int id;
    @SerializedName("empcode")
    private String empcode;
    @SerializedName("empname")
    private String empname;
    @SerializedName("departmentid")
    private int departmentid;
    @SerializedName("depname")
    private String depname;

    public void setId(int id) {
        this.id = id;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public int getId() {
        return id;
    }

    public String getEmpcode() {
        return empcode;
    }

    public String getEmpname() {
        return empname;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public String getDepname() {
        return depname;
    }
}

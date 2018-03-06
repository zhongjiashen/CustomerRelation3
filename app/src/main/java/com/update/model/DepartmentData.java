package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/6 0006 下午 2:19
 * Description:部门信息
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/6 0006         申中佳               V1.0
 */
public class DepartmentData {

    /**
     * id : 1
     * depcode : 01
     * depname : 总部
     */

    @SerializedName("id")
    private int id;
    @SerializedName("depcode")
    private String depcode;
    @SerializedName("depname")
    private String depname;

    public void setId(int id) {
        this.id = id;
    }

    public void setDepcode(String depcode) {
        this.depcode = depcode;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public int getId() {
        return id;
    }

    public String getDepcode() {
        return depcode;
    }

    public String getDepname() {
        return depname;
    }
}

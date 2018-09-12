package com.update.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/5 0005 上午 11:37
 * Description:数据字典
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/5 0005         申中佳               V1.0
 */
public class DataDictionaryData {


    /**
     * id : 0
     * dictmc : 普通
     */

    @SerializedName("id")
    private int id;
    @SerializedName("dictmc")
    private String dictmc;

    public DataDictionaryData(int id, String dictmc) {
        this.id = id;
        this.dictmc = dictmc;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setDictmc(String dictmc) {
        this.dictmc = dictmc;
    }

    public int getId() {
        return id;
    }

    public String getDictmc() {
        return dictmc;
    }
}

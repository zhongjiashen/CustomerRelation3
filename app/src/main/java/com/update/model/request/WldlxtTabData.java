package com.update.model.request;

import com.google.gson.annotations.SerializedName;

public class WldlxtTabData {

    /**
     * id : 1
     * dictmc : tb_received
     */

    @SerializedName("id")
    private int id;
    @SerializedName("dictmc")
    private String dictmc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDictmc() {
        return dictmc;
    }

    public void setDictmc(String dictmc) {
        this.dictmc = dictmc;
    }
}

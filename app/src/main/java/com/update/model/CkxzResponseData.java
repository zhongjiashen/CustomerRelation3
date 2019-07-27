package com.update.model;

public class CkxzResponseData {

    /**
     * id : 3
     * name : 一号仓库
     */

    private int id;
    private String name;

    public CkxzResponseData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

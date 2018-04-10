package com.update.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1363655717 on 2018-04-10.
 */

public class RqLogisticsCompanyData {

    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("cname")
    private String cname;
    @SerializedName("types")
    private int types;
    @SerializedName("typesname")
    private String typesname;
    @SerializedName("isused")
    private int isused;
    @SerializedName("balance")
    private double balance;
    @SerializedName("suramt")
    private double suramt;
    @SerializedName("oweamt")
    private double oweamt;
    @SerializedName("lxrid")
    private Object lxrid;
    @SerializedName("lxrname")
    private Object lxrname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("shipto")
    private Object shipto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public String getTypesname() {
        return typesname;
    }

    public void setTypesname(String typesname) {
        this.typesname = typesname;
    }

    public int getIsused() {
        return isused;
    }

    public void setIsused(int isused) {
        this.isused = isused;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSuramt() {
        return suramt;
    }

    public void setSuramt(double suramt) {
        this.suramt = suramt;
    }

    public double getOweamt() {
        return oweamt;
    }

    public void setOweamt(double oweamt) {
        this.oweamt = oweamt;
    }

    public Object getLxrid() {
        return lxrid;
    }

    public void setLxrid(Object lxrid) {
        this.lxrid = lxrid;
    }

    public Object getLxrname() {
        return lxrname;
    }

    public void setLxrname(Object lxrname) {
        this.lxrname = lxrname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getShipto() {
        return shipto;
    }

    public void setShipto(Object shipto) {
        this.shipto = shipto;
    }
}

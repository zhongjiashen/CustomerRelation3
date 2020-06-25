package com.cr.activity.response

import com.google.gson.annotations.SerializedName


data class XsddMasterResponseData(
        @SerializedName("amount")
        var amount: Double = 0.0,
        @SerializedName("bankid")
        var bankid: String = "",
        @SerializedName("bankname")
        var bankname: String = "",
        @SerializedName("billdate")
        var billdate: String = "",
        @SerializedName("billtypeid")
        var billtypeid: Int = 0,
        @SerializedName("billtypename")
        var billtypename: String = "",
        @SerializedName("clientid")
        var clientid: Int = 0,
        @SerializedName("cname")
        var cname: String = "",
        @SerializedName("code")
        var code: String = "",
        @SerializedName("contator")
        var contator: String = "",
        @SerializedName("departmentid")
        var departmentid: Int = 0,
        @SerializedName("depname")
        var depname: String = "",
        @SerializedName("empname")
        var empname: String = "",
        @SerializedName("exemanid")
        var exemanid: Int = 0,
        @SerializedName("linkmanid")
        var linkmanid: String = "",
        @SerializedName("memo")
        var memo: String = "",
        @SerializedName("opid")
        var opid: Int = 0,
        @SerializedName("opname")
        var opname: String = "",
        @SerializedName("phone")
        var phone: String = "",
        @SerializedName("projectid")
        var projectid: String = "",
        @SerializedName("projectname")
        var projectname: String = "",
        @SerializedName("readonly")
        var readonly: Int = 0,
        @SerializedName("shipto")
        var shipto: String = "",
        @SerializedName("shzt")
        var shzt: Int = 0,
        @SerializedName("sorderid")
        var sorderid: Int = 0,
        @SerializedName("suramt")
        var suramt: Double = 0.0,
        @SerializedName("taxrate")
        var taxrate: String = "",
        @SerializedName("takedate")
        var takedate: String = ""


)
package com.cr.activity.jxc

import com.google.gson.annotations.SerializedName

data class KtXzYydMastData(
        @SerializedName("billid") var billid: String = "",
        @SerializedName("code") var code: String = "",
        @SerializedName("billdate") var billdate: String = "",
        @SerializedName("clientid") var clientid: String = "",
        @SerializedName("cname") var cname: String = "",
        @SerializedName("reftypeid") var reftypeid: String = "",
        @SerializedName("billtypeid") var billtypeid: String = "",
        @SerializedName("billtypename") var billtypename: String = "",
        @SerializedName("linkmanid") var linkmanid: String = "",
        @SerializedName("contator") var contator: String = "",
        @SerializedName("phone") var phone: String = "",
        @SerializedName("shipto") var shipto: String = "",
        @SerializedName("projectid") var projectid: String = "",
        @SerializedName("projectname") var projectname: String = "",
        @SerializedName("taxrate") var taxrate: String = "0",
        var isCheck: Boolean = false
)
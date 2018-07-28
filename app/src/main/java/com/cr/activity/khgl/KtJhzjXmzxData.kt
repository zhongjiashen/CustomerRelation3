package com.cr.activity.khgl

import com.google.gson.annotations.SerializedName

data class KtJhzjXmzxData(
        @SerializedName("itemid") var itemid: Int = 0,
        @SerializedName("jhid") var jhid: Int = 0,
        @SerializedName("items") var items: String = "",
        @SerializedName("jhnr") var jhnr: String = "",
        @SerializedName("zdcl") var zdcl: String = "",
        @SerializedName("zxcl") var zxcl: String = "",
        @SerializedName("zxjg") var zxjg: Int = 0,
        @SerializedName("zxjgname") var zxjgname: Any? = Any(),
        @SerializedName("zxyy") var zxyy: String = "",
        @SerializedName("shzt") var shzt: Int = 0,
        @SerializedName("issy") var issy: Int = 0,
        @SerializedName("syrq") var syrq: Any? = Any(),
        @SerializedName("opid") var opid: Int = 0,
        @SerializedName("opname") var opname: String = "",
        @SerializedName("isused") var isused: Int = 0,
        @SerializedName("startdate") var startdate: String = "",
        @SerializedName("starttime") var starttime: String = "",
        @SerializedName("zjlx") var zjlx:String = "",
        @SerializedName("zjlxname") var zjlxname: String = "",
        @SerializedName("chanceid") var chanceid:  String = "",
        @SerializedName("clientid") var clientid:  String = "",
        @SerializedName("cname") var cname: String = "",
        @SerializedName("gmid") var gmid:  String = "",
        @SerializedName("gmmc") var gmmc: String = ""
)
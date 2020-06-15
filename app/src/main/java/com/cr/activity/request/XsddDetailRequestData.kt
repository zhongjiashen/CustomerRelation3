package com.cr.activity.request

import com.google.gson.annotations.SerializedName

data class XsddDetailRequestData(
        /**
         * 0或空表示新增
         */
        @SerializedName("billid") var billid: String = "0",
        @SerializedName("itemno") var itemno: String = "0",
        @SerializedName("goodsid") var goodsid: String = "",
        @SerializedName("unitid") var unitid: String = "",
        @SerializedName("unitprice") var unitprice: String = "",
        @SerializedName("unitqty") var unitqty: String = "",
        @SerializedName("disc") var disc: String = "100",
        @SerializedName("amount") var amount: String = "",
        @SerializedName("batchcode") var batchcode: String = "",
        @SerializedName("produceddate") var produceddate: String = "",
        @SerializedName("validdate") var validdate: String = "",
        @SerializedName("batchrefid") var batchrefid: String = "",
        @SerializedName("refertype") var refertype: String = "",
        @SerializedName("referbillid") var referbillid: String = "",
        @SerializedName("referitemno") var referitemno: String = "",
        @SerializedName("taxrate") var taxrate: String = "",
        @SerializedName("taxunitprice ") var taxunitprice: String = "",
        @SerializedName("memo") var memo: String = ""
)
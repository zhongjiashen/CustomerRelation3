package com.cr.activity.request

import com.google.gson.annotations.SerializedName

data class XsddMasterRequestData(
        /**
         * 0或空表示新增
         */
        @SerializedName("billid") var billid: String = "0",
        @SerializedName("code") var code: String = "",
        /**
         * 客户id
         */
        @SerializedName("clientid") var clientid: String = "",
        /**
         * 联系人id
         */
        @SerializedName("linkmanid") var linkmanid: String = "",
        @SerializedName("phone") var phone: String = "",
        /**
         * 交货地址
         */
        @SerializedName("shipto") var shipto: String = "",
        /**
         * 发票类型
         */
        @SerializedName("billtypeid") var billtypeid: String = "",
        @SerializedName("projectid") var projectid: String = "",
        /**
         * 交货日期
         */
        @SerializedName("takedate") var takedate: String = "",
        /**
         * 资金账户id
         */
        @SerializedName("bankid") var bankid: String = "",
        /**
         * 预售金额
         */
        @SerializedName("suramt ") var suramt: String = "",
        /**
         * 合计金额
         */
        @SerializedName("amount") var amount: String = "",
        /**
         * 单据日期
         */
        @SerializedName("billdate") var billdate: String = "",
        @SerializedName("departmentid") var departmentid: String = "",
        @SerializedName("exemanid") var exemanid: String = "",


        @SerializedName("memo") var memo: String = "",
        @SerializedName("opid") var opid: String = ""

)
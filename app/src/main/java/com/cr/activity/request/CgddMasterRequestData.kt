package com.cr.activity.request

import com.google.gson.annotations.SerializedName

data class CgddMasterRequestData(
        /**
         * 0或空表示新增
         */
        @SerializedName("billid") var billid: String = "0",
        @SerializedName("code") var code: String = "",
        /**
         * 单据日期
         */
        @SerializedName("billdate") var billdate: String = "",
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
         * 送货地址
         */
        @SerializedName("billto") var billto: String = "",
        /**
         * 交货日期
         */
        @SerializedName("revdate") var revdate: String = "",
        /**
         * 发票类型
         */
        @SerializedName("billtypeid") var billtypeid: String = "",
        /**
         * 合计金额
         */
        @SerializedName("amount") var amount: String = "",
        @SerializedName("exemanid") var exemanid: String = "",
        @SerializedName("memo") var memo: String = "",
        @SerializedName("opid") var opid: String = "",


        @SerializedName("projectid") var projectid: String = "",
        @SerializedName("departmentid") var departmentid: String = "",
        /**
         * 资金账户id
         */
        @SerializedName("bankid") var bankid: String = "",
        /**
         * 预收金额
         */
        @SerializedName("suramt ") var suramt: String = ""


        )
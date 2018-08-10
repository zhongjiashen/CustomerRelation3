package com.update.model

import com.google.gson.annotations.SerializedName

/**
 * 物流信息界面实体类
 */
data class KtWlxxData(
        //物流公司ID
        @SerializedName("logisticid") var logisticid: String = "",
        //物流公司名字
        @SerializedName("logisticname") var logisticname: String = "",
        //物流单号
        @SerializedName("shipno") var shipno: String = "",
        //运输方式
        @SerializedName("shiptype") var shiptype: String = "",
        //运费承担 0我方 1对方
        @SerializedName("beartype") var beartype: String = "",
        //付款类型 0往来结算 1现款结算
        @SerializedName("logisticispp") var logisticispp : String = "",
        //-付款账户ID
        @SerializedName("logisticbankid") var logisticbankid : String = "",
        //-付款账户账号
        @SerializedName("logisticbankaccount") var logisticbankaccount : String = "",
        //运费
        @SerializedName("amount") var amount : String = "",
        //通知放货 0否 1是
        @SerializedName("isnotice") var isnotice : String = ""

)
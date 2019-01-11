package com.cr.activity.jxc

import com.google.gson.annotations.SerializedName
import com.update.model.Serial

data class KtXzspData(
        @SerializedName("goodsid") var goodsid: Int = 0,
        @SerializedName("code") var code: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("specs") var specs: String = "",
        @SerializedName("model") var model: String = "",
        @SerializedName("inf_costingtypeid") var infCostingtypeid: Int = 0,
        @SerializedName("goodstypeid") var goodstypeid: Int = 0,
        @SerializedName("goodstypename") var goodstypename: String = "",
        @SerializedName("onhand") var onhand: Double = 0.0,
        @SerializedName("aprice") var aprice: Double = 0.0,//单价
        @SerializedName("unitid") var unitid: Int = 0,
        @SerializedName("unitname") var unitname: String = "",
        @SerializedName("batchctrl") var batchctrl: String = "",
        @SerializedName("serialctrl") var serialctrl: String = "",
        var isCheck: Boolean = false,
        /**
         * 备注
         */
        @SerializedName("memo") var memo: String =  "",//备注
        @SerializedName("number")var number:Double = 1.0,//数量
        @SerializedName("serialinfo") var serialinfo: String= "",//序列号
        @SerializedName("mSerials")var mSerials: List<Serial>? = null,//序列号临时保存集合
        @SerializedName("taxrate")var taxrate: String = "",//税率
        @SerializedName("taxunitprice")var taxunitprice: String = ""//含税单价
)
package com.cr.activity.jxc.ckgl.ckdb

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class KtJxcCkgdbXzspData(
        @SerializedName("goodsid") var goodsid: Int = 0,
        @SerializedName("code") var code: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("specs") var specs: String = "",
        @SerializedName("model") var model: String = "",
        @SerializedName("inf_costingtypeid") var infCostingtypeid: Int = 0,
        @SerializedName("goodstypeid") var goodstypeid: Int = 0,
        @SerializedName("goodstypename") var goodstypename: String = "",
        @SerializedName("onhand") var onhand: Double = 0.0,
        @SerializedName("aprice") var aprice: Double = 0.0,
        @SerializedName("unitid") var unitid: Int = 0,
        @SerializedName("unitname") var unitname: String = "",
        @SerializedName("batchctrl") var batchctrl: String = "",
        @SerializedName("serialctrl") var serialctrl: String = "",
        var cpph: String = "",
        var scrq: String = "",
        var yxqz: String = "",
        var sl: String = "1",
        var dj: String = "",
        var isCheck: Boolean = false
):Serializable
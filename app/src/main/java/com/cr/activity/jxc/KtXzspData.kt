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
        @SerializedName("onhand") var onhand: Double = 0.0,//库存
        @SerializedName("aprice") var aprice: Double = 0.0,//单价
        @SerializedName("unitid") var unitid: Int = 0,//数量单位id
        @SerializedName("unitname") var unitname: String = "",//数量单位名称
        @SerializedName("batchctrl") var batchctrl: String = "",//批次商品T
        @SerializedName("serialctrl") var serialctrl: String = "",//严格序列商品T
        var isCheck: Boolean = false,
        @SerializedName("memo") var memo: String =  "",//备注
        @SerializedName("cpph") var cpph: String =  "",//产品编号
        @SerializedName("batchrefid") var batchrefid: String =  "",//产品编号id
        @SerializedName("scrq") var scrq: String =  "",//生产日期
        @SerializedName("yxqz") var yxqz: String =  "",//有效期至

        @SerializedName("number")var number:Double = 1.0,//数量
        @SerializedName("serialinfo") var serialinfo: String= "",//序列号
        @SerializedName("mSerials")var mSerials: List<Serial> = ArrayList(),//序列号临时保存集合
        @SerializedName("taxrate")var taxrate: String = "",//税率
        @SerializedName("taxunitprice")var taxunitprice: String = ""//含税单价
)
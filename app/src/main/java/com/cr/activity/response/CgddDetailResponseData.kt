package com.cr.activity.response


import com.cr.activity.jxc.KtXzspData
import com.google.gson.annotations.SerializedName

data class CgddDetailResponseData(
        @SerializedName("amount")
        var amount: String = "",
        @SerializedName("batchcode")
        var batchcode: String = "",
        @SerializedName("batchrefid")
        var batchrefid: String = "",
        @SerializedName("disc")
        var disc: Double = 0.0,
        @SerializedName("goodscode")
        var goodscode: String = "",
        @SerializedName("goodsid")
        var goodsid: String = "",
        @SerializedName("goodsname")
        var goodsname: String = "",
        @SerializedName("itemno")
        var itemno: String = "",
        @SerializedName("memo")
        var memo: String = "",
        @SerializedName("model")
        var model: String = "",
        @SerializedName("produceddate")
        var produceddate: String = "",
        @SerializedName("referbillid")
        var referbillid: String = "",
        @SerializedName("referitemno")
        var referitemno: String = "",
        @SerializedName("refertype")
        var refertype: String = "",
        @SerializedName("sorderid")
        var sorderid: String = "",
        @SerializedName("specs")
        var specs: String = "",
        @SerializedName("taxrate")
        var taxrate: String = "",
        @SerializedName("taxunitprice")
        var taxunitprice: String = "",
        @SerializedName("unitid")
        var unitid: String = "",
        @SerializedName("unitname")
        var unitname: String = "",
        @SerializedName("unitprice")
        var unitprice: String = "",
        @SerializedName("unitqty")
        var unitqty: String = "",
        @SerializedName("userdef1")
        var userdef1: String = "",
        @SerializedName("userdef2")
        var userdef2: String = "",
        @SerializedName("userdef3")
        var userdef3: String = "",
        @SerializedName("userdef4")
        var userdef4: String = "",
        @SerializedName("userdef5")
        var userdef5: String = "",
        @SerializedName("validdate")
        var validdate: String = ""
){
    constructor(ktXzspData: KtXzspData) : this() {
        goodscode=ktXzspData.code
        goodsname=ktXzspData.name
        specs=ktXzspData.specs
        model=ktXzspData.model
        goodsid = ktXzspData.goodsid.toString() + ""
        unitid = ktXzspData.unitid.toString() + ""
        unitprice = ktXzspData.aprice.toString() + ""
        unitqty = ktXzspData.number.toString() + ""
        unitname=ktXzspData.unitname
        amount = (ktXzspData.taxunitprice.toDouble() * ktXzspData.number).toString() + ""
        batchcode = ktXzspData.cpph
        produceddate = ktXzspData.scrq
        validdate = ktXzspData.yxqz
        batchrefid = ktXzspData.batchrefid
        taxrate = ktXzspData.taxrate
        taxunitprice = ktXzspData.taxunitprice
        memo = ktXzspData.memo
    }

}
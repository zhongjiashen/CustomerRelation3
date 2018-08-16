package com.update.model

import com.google.gson.annotations.SerializedName

data class KtCwbbmxData(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("billdate") var billdate: String = "",
        @SerializedName("subname") var subname: String = "",
        @SerializedName("billtypename") var billtypename: String = "",
        @SerializedName("code") var code: String = "",
        @SerializedName("cname") var cname: String = "",
        @SerializedName("amount") var amount: Double = 0.0,
        @SerializedName("purappamt") var purappamt: Double = 0.0
)
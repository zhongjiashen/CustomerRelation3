package com.update.model

import com.google.gson.annotations.SerializedName

data class KtLrbData(
        @SerializedName("subid") var subid: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("curbalance") var curbalance: Double = 0.0,
        @SerializedName("endbalance") var endbalance: Double = 0.0,
        @SerializedName("detailflag") var detailflag: String = "",
        @SerializedName("usercode") var usercode: String = "",
        @SerializedName("inibalance") var inibalance: Double = 0.0
)
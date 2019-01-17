package com.cr.activity.jxc

import com.google.gson.annotations.SerializedName

data class JxcXzphData(
        @SerializedName("batchcode") var batchcode: String = "",
        @SerializedName("produceddate") var produceddate: String = "",
        @SerializedName("validdate") var validdate: String = "",
        @SerializedName("aprice") var aprice: Double = 0.0,
        @SerializedName("onhand") var onhand: Double = 0.0,
        @SerializedName("id") var id: Int = 0
)
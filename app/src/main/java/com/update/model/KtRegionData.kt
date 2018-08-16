package com.update.model

import com.google.gson.annotations.SerializedName

/**
 * 区域
 */
data class KtRegionData(
        @SerializedName("id") var id: String = "",
        @SerializedName("cname") var cname: String = ""
)
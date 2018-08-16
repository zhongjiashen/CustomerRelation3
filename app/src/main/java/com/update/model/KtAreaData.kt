package com.update.model

import com.google.gson.annotations.SerializedName

/**
 * 区域
 */
data class KtAreaData(
        @SerializedName("id") var id: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("dictmc") var taxrate: String = ""
)
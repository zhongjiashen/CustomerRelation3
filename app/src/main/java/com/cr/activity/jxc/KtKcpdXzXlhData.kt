package com.cr.activity.jxc

import com.google.gson.annotations.SerializedName

data class KtKcpdXzXlhData(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("serno") var serno: String = "",
        var type: Int = 0,
        var isCheck: Boolean =false
)
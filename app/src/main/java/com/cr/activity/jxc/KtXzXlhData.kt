package com.cr.activity.jxc

import com.google.gson.annotations.SerializedName

data class KtXzXlhData(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("serno") var serno: String = "",
        var isCheck: Boolean = false
)
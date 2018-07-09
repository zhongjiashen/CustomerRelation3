package com.update.model

import com.google.gson.annotations.SerializedName

data class KtFplxData(
        @SerializedName("id") var id: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("taxrate") var taxrate: String = ""
)
package com.cr.activity.khgl.jhzj
import com.google.gson.annotations.SerializedName



data class KtJhzjData(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("jhlx") var jhlx: Int = 0,
    @SerializedName("jhinfo") var jhinfo: String = "",
    @SerializedName("zwzj") var zwzj: String = "",
    @SerializedName("nextjhinfo") var nextjhinfo:  String = ""
)
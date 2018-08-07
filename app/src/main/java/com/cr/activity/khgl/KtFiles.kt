package com.cr.activity.khgl

import com.google.gson.annotations.SerializedName

class KtFiles(
        @SerializedName("lb") var lb: String = "",
        @SerializedName("filenames") var filenames: String = "",
        @SerializedName("xx") var xx: String = ""
)
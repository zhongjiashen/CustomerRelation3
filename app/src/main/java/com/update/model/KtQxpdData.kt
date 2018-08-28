package com.update.model
import com.google.gson.annotations.SerializedName



data class KtQxpdData(
    @SerializedName("menuid") var menuid: String = "",
    @SerializedName("menuname") var menuname: String = "",
    @SerializedName("n_explore") var nExplore: Int = 0,
    @SerializedName("n_insert") var nInsert: Int = 0,
    @SerializedName("n_update") var nUpdate: Int = 0,
    @SerializedName("n_delete") var nDelete: Int = 0
)
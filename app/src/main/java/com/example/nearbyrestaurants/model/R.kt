package com.example.nearbyrestaurants.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class R(
    @SerializedName("has_menu_status")
    @Expose
    var hasMenuStatus: HasMenuStatus?,
    @SerializedName("res_id")
    @Expose
    var resId: Int?,
    @SerializedName("is_grocery_store")
    @Expose
    var isGroceryStore: Boolean?
)
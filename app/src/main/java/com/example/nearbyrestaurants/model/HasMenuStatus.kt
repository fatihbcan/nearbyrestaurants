package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HasMenuStatus(
    @SerializedName("delivery")
    @Expose
    val delivery: Int,
    @SerializedName("takeaway")
    @Expose
    val takeaway: Int
)
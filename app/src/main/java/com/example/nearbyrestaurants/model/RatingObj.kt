package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatingObj(
    @SerializedName("title")
    @Expose
    var title: Title?,

    @SerializedName("bg_color")
    @Expose
    var bgColor: BgColor?
)
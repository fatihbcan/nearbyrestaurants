package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("text")
    @Expose
    var text: String?
)
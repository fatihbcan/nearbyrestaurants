package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("review")
    @Expose
    var review: List<Any>?
)
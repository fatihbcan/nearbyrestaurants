package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("restaurant")
    @Expose
    var restaurant: Restaurant_?
)
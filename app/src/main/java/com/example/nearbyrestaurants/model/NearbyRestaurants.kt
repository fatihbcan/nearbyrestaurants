package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NearbyRestaurants(
    @SerializedName("results_found")
    @Expose
    var resultsFound: Int?,

    @SerializedName("results_start")
    @Expose
    var resultsStart: Int?,

    @SerializedName("results_shown")
    @Expose
    var resultsShown: Int?,

    @SerializedName("restaurants")
    @Expose
    var restaurants: List<Restaurant>?
)
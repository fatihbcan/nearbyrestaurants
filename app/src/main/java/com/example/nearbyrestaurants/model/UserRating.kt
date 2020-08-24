package com.example.nearbyrestaurants.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("aggregate_rating")
    @Expose
    var aggregateRating: String?,

    @SerializedName("rating_text")
    @Expose
    var ratingText: String?,

    @SerializedName("rating_color")
    @Expose
    var ratingColor: String?,

    @SerializedName("rating_obj")
    @Expose
    var ratingObj: RatingObj?,

    @SerializedName("votes")
    @Expose
    var votes: Int?
)
package com.example.nearbyrestaurants.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ZomatoEndpoints {
    @Headers(
        "Accept: application/json",
        "user-key: 61528551ffc800703d600cb2c25e6900"
    )
    @GET("search")
    fun getRestaurants(
        @Query("count") count: Int,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Call<NearbyRestaurants>
}
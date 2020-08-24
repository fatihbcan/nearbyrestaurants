package com.example.nearbyrestaurants.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nearbyrestaurants.model.NearbyRestaurants
import com.example.nearbyrestaurants.model.Restaurant
import com.example.nearbyrestaurants.model.ServiceBuilder
import com.example.nearbyrestaurants.model.ZomatoEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRepository {
    fun callMyRetrofitApi(liveData: MutableLiveData<List<Restaurant>>, latitude:Double, longitude:Double){
        val request = ServiceBuilder.buildService(ZomatoEndpoints::class.java)
        val call = request.getRestaurants(5,latitude,longitude,"real_distance","desc") // the nearest restaurants
        //val call = request.getRestaurants(5,latitude,longitude,"rating","desc") // best rating restaurants in the city

        call.enqueue(object : Callback<NearbyRestaurants> {
            override fun onResponse(
                call: Call<NearbyRestaurants>,
                response: Response<NearbyRestaurants>
            ) {
                if(response.isSuccessful){
                    liveData.value = response.body()!!.restaurants
                }
            }

            override fun onFailure(call: Call<NearbyRestaurants>, t: Throwable) {
                liveData.value = null
            }

        })
    }
}
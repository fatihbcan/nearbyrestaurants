package com.example.nearbyrestaurants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nearbyrestaurants.model.Restaurant



class MainViewModel : ViewModel(){
    val liveNearbyRestaurants = MutableLiveData<List<Restaurant>>()
    val liveLoading = MutableLiveData<Boolean>()


    private val myRepository = MyRepository()

    fun callApi(lat: Double,longi: Double){
        myRepository.callMyRetrofitApi(liveNearbyRestaurants,lat,longi)
        liveLoading.value = liveNearbyRestaurants==null
    }
}
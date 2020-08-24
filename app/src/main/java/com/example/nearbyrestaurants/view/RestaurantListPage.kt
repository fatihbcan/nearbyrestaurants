package com.example.nearbyrestaurants.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbyrestaurants.R
import com.example.nearbyrestaurants.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_restaurant_list_page.*


class RestaurantListPage : Fragment() {
    lateinit var fusedLocationClient : FusedLocationProviderClient
    val PERMISSION_ID = 1010
    lateinit var myViewModel: MainViewModel
    private val restaurantAdapter = RestaurantAdapter(arrayListOf())

    companion object {
        var lat: Double = 0.0
        var lon: Double = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        myViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        if (checkLocationPermission()){
            getLatLong()
        }else{
            getLatLong()
        }


        observeResponseData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list_page, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.restaurantRecycler)
        recyclerView.adapter = restaurantAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }


    private fun getLatLong(){
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if(location != null) {
                lat = location.latitude
                lon = location.longitude
                myViewModel.callApi(lat,lon)

            }
        }
    }
    private fun observeResponseData(){
        myViewModel.liveNearbyRestaurants.observe(this, Observer {
                data -> data?.let { restaurantAdapter.updateRestaurants(it) }
        })

        myViewModel.liveLoading.observe(this, Observer {
                isLoading -> isLoading?.let {
            progress_bar.visibility = if(it) View.VISIBLE else View.GONE
        }
        })
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_ID -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if ((ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED)
                    ) {

                        getLatLong()
                    }
                } else {
                    // permission denied, Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
        // other 'case' lines to check for other
        // permissions this app might request
    }
    private fun checkLocationPermission(): Boolean {
        if ((ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID
                )
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID
                )
            }
            return false
        } else {
            return true
        }
    }

}
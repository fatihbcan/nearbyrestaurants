package com.example.nearbyrestaurants.view


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.nearbyrestaurants.R
import com.example.nearbyrestaurants.util.loadImage
import kotlinx.android.synthetic.main.fragment_restaurant_detail_page.view.*


class RestaurantDetailPage : Fragment() {

    val args: RestaurantDetailPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_detail_page, container, false)

        val restaurantTitle = args.details.name
        val restaurantCuisine = "Cuisine : "+args.details.cuisines
        val restaurantCurrency = "Currency : "+args.details.currency
        val restaurantTiming = "Timings : "+args.details.timings
        val restaurantPhone = "Phone Number : "+args.details.phoneNumbers
        val restaurantImage = args.details.featuredImage
        val rating = args.details.userRating!!.aggregateRating
        val latitude = args.details.location!!.latitude
        val longitude = args.details.location!!.longitude
        val address = args.details.location!!.address

        view.denemetoolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        view.denemetoolbar.setOnClickListener { Navigation.findNavController(view).navigate(R.id.returnToListPage) }

        view.title_name.setText(restaurantTitle)
        view.cuisines.setText(restaurantCuisine)
        view.currency.setText(restaurantCurrency)
        view.timings.setText(restaurantTiming)
        view.phoneNumber.setText(restaurantPhone)
        view.header_image.loadImage(restaurantImage)
        view.ratingBar.rating = rating!!.toFloat()

        view.map.setOnClickListener(View.OnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:{$latitude},{$longitude}?z=10&q={$address}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        })

        return view
    }


}
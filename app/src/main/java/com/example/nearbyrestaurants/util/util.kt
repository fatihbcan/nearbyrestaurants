package com.example.nearbyrestaurants.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nearbyrestaurants.R


fun ImageView.loadImage(uri: String?){
    val options = RequestOptions()
        .error(R.drawable.headerdefault)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}


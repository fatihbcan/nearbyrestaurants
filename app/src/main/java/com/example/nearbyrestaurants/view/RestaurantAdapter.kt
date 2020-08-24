package com.example.nearbyrestaurants.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbyrestaurants.R
import com.example.nearbyrestaurants.model.Restaurant
import com.example.nearbyrestaurants.util.loadImage

class RestaurantAdapter (private val restaurants: ArrayList<Restaurant>): RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val action = RestaurantListPageDirections.goToDetailPage(
                restaurants[position].restaurant!!
            )
            Navigation.findNavController(it).navigate(action) }
        return holder.bind(restaurants[position])
        }

        fun updateRestaurants(newRestaurants: List<Restaurant>) {
            restaurants.clear()
            restaurants.addAll(newRestaurants)
            notifyDataSetChanged()
        }

    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val restoranIsm: TextView = itemView.findViewById(R.id.restaurantName)
        private val restoranResm: ImageView = itemView.findViewById(R.id.restImage)

        fun bind(restaurants: Restaurant) {
            restoranIsm.text = restaurants.restaurant!!.name
            restoranResm.loadImage(restaurants.restaurant!!.featuredImage)

        }
    }

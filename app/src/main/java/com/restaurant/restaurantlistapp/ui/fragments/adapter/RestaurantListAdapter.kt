package com.restaurant.restaurantlistapp.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.restaurantlistapp.R
import com.restaurant.restaurantlistapp.databinding.ItemRestaurantBinding
import com.restaurant.restaurantlistapp.domain.model.Restaurants

/**
 * @Author: Akash Abhishek
 * @Date: 02 July 2022
 */

class RestaurantListAdapter :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    private var listRestaurant = arrayListOf<Restaurants.Restaurant>()

    /**
     * Using notify data set changed as all the list item will change everytime to show applied filter name
     * In other case would have used Diff Utils
     */

    fun setData(list: List<Restaurants.Restaurant>) {
        listRestaurant.clear()
        list.forEach { listRestaurant.add(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RestaurantViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_restaurant,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.binding.item = listRestaurant[position]
    }

    class RestaurantViewHolder(val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listRestaurant.size
    }

}
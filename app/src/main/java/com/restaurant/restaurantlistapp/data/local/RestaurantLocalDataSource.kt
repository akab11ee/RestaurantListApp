package com.restaurant.restaurantlistapp.data.local

import com.restaurant.restaurantlistapp.data.RestaurantsRepoImpl
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsDataSource
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import timber.log.Timber
import javax.inject.Inject

/**
 * Fetch restaurant json from [RestaurantsProvider] and return to [RestaurantsRepoImpl]
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

class RestaurantLocalDataSource @Inject constructor(private val restaurantsProvider: RestaurantsProvider) :
    RestaurantsDataSource.Local {
    override suspend fun fetchRestaurants(): RestaurantsDto {
        val restaurants = restaurantsProvider.fetchRestaurants()

        Timber.d("restaurant list is fetched")
        return restaurants
    }
}
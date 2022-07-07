package com.restaurant.restaurantlistapp.data.local

import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsDataSource
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

class RestaurantLocalDataSource @Inject constructor(private val restaurantsProvider: RestaurantsProvider) :
    RestaurantsDataSource.Local {
    override suspend fun fetchRestaurants(): RestaurantsDto {
        val restaurants = restaurantsProvider.fetchRestaurants()

        Timber.d("restaurant list is fetched")
        return restaurants
    }
}
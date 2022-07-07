package com.restaurant.restaurantlistapp.data.memory

import com.restaurant.restaurantlistapp.data.RestaurantsRepoImpl
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsDataSource
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import timber.log.Timber

/**
 * Save and fetch data from memory and return to [RestaurantsRepoImpl]
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

class RestaurantMemoryDataSource : RestaurantsDataSource.Memory {
    private var restaurantsDto: RestaurantsDto? = null

    override fun cacheInMemory(dto: RestaurantsDto) {
        restaurantsDto = dto
    }

    override fun fetchRestaurants(): RestaurantsDto? {
        if (restaurantsDto != null) Timber.d("got result from cache")
        return restaurantsDto
    }
}
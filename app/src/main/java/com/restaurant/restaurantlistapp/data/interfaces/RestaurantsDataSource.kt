package com.restaurant.restaurantlistapp.data.interfaces

import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto

/**
 * * To fetch data from memory or local
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

interface RestaurantsDataSource {

    interface Memory {
        fun cacheInMemory(dto: RestaurantsDto)

        fun fetchRestaurants(): RestaurantsDto?
    }

    interface Local {
        suspend fun fetchRestaurants(): RestaurantsDto
    }
}
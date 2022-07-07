package com.restaurant.restaurantlistapp.data.interfaces

import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto

interface RestaurantsDataSource {

    interface Memory {
        fun cacheInMemory(dto: RestaurantsDto)

        fun fetchRestaurants(): RestaurantsDto?
    }

    interface Local {
        suspend fun fetchRestaurants(): RestaurantsDto
    }
}
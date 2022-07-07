package com.restaurant.restaurantlistapp.data.interfaces

import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun fetchRestaurants(): Flow<RestaurantsDto>
}
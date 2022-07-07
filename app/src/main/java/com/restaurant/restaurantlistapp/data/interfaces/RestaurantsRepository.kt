package com.restaurant.restaurantlistapp.data.interfaces

import com.restaurant.restaurantlistapp.data.RestaurantsRepoImpl
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import com.restaurant.restaurantlistapp.domain.usecase.RestaurantsFetchUseCase
import kotlinx.coroutines.flow.Flow

/**
 * * To make an interaction between [RestaurantsRepoImpl] & [RestaurantsFetchUseCase]
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

interface RestaurantsRepository {
    suspend fun fetchRestaurants(): Flow<RestaurantsDto>
}
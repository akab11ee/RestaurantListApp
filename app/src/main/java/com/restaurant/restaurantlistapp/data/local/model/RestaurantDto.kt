package com.restaurant.restaurantlistapp.data.local.model

import androidx.annotation.Keep
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsRepository
import com.restaurant.restaurantlistapp.domain.usecase.RestaurantsFetchUseCase
import kotlinx.serialization.Serializable

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

@Serializable
@Keep
data class RestaurantsDto(
    val restaurants: List<RestaurantDto>?
) {
    @Keep
    @Serializable
    data class RestaurantDto(
        val id: String,
        val name: String,
        val sortingValues: SortingValuesDto,
        val status: String?
    ) {
        @Keep
        @Serializable
        data class SortingValuesDto(
            val averageProductPrice: Int,
            val bestMatch: Double,
            val deliveryCosts: Int,
            val distance: Int,
            val minCost: Int,
            val newest: Double,
            val popularity: Double,
            val ratingAverage: Double
        )
    }
}

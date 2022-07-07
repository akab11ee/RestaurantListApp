package com.restaurant.restaurantlistapp.domain.model

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

data class Restaurants(
    val restaurants: List<Restaurant> = listOf()
) {
    data class Restaurant(
        val id: Double,
        val name: String,
        val sortingValues: SortingValues,
        var sortDetail: SortDetail,
        val status: Status
    ) {
        data class SortingValues(
            val averageProductPrice: Double,
            val bestMatch: Double,
            val deliveryCosts: Double,
            val distance: Double,
            val minCost: Double,
            val newest: Double,
            val popularity: Double,
            val ratingAverage: Double
        )
    }
}

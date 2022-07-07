package com.restaurant.restaurantlistapp.domain.model

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

data class SortDetail(
    var sortName: SortByValues = SortByValues.BEST_MATCH,
    var sortValue: Double = 0.0
)
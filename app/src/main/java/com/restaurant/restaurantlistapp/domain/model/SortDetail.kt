package com.restaurant.restaurantlistapp.domain.model

data class SortDetail(
    var sortName: SortByValues = SortByValues.BEST_MATCH,
    var sortValue: Double = 0.0
)
package com.restaurant.restaurantlistapp.domain.mapper

import androidx.annotation.VisibleForTesting
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import com.restaurant.restaurantlistapp.domain.model.Restaurants
import com.restaurant.restaurantlistapp.domain.model.Restaurants.Restaurant
import com.restaurant.restaurantlistapp.domain.model.Restaurants.Restaurant.SortingValues
import com.restaurant.restaurantlistapp.domain.model.SortByValues
import com.restaurant.restaurantlistapp.domain.model.SortDetail
import com.restaurant.restaurantlistapp.domain.model.Status
import javax.inject.Inject

class RestaurantsMapper @Inject constructor() {

    fun mapToRestaurants(dto: RestaurantsDto) = Restaurants(
        restaurants = dto.restaurants?.map(::mapToRestaurant)
            ?.sortedWith(compareBy<Restaurant> { it.status.priority }.thenByDescending { it.sortingValues.bestMatch })
            .orEmpty()
    )

    @VisibleForTesting
    fun mapToRestaurant(dto: RestaurantsDto.RestaurantDto): Restaurant = Restaurant(
        id = dto.id.toDouble(),
        name = dto.name,
        status = mapToStatus(dto.status),
        sortingValues = mapToSortingValue(dto.sortingValues),
        sortDetail = SortDetail(
            sortName = SortByValues.BEST_MATCH,
            sortValue = dto.sortingValues.bestMatch
        )
    )

    @VisibleForTesting
    fun mapToStatus(status: String?) =
        Status.values().find {
            it.toString().lowercase().replace("_", " ") == status
        } ?: Status.CLOSED

    @VisibleForTesting
    fun mapToSortingValue(dto: RestaurantsDto.RestaurantDto.SortingValuesDto): SortingValues =
        SortingValues(
            bestMatch = dto.bestMatch,
            deliveryCosts = dto.deliveryCosts.toDouble(),
            distance = dto.distance.toDouble(),
            minCost = dto.minCost.toDouble(),
            newest = dto.newest,
            popularity = dto.popularity,
            ratingAverage = dto.ratingAverage,
            averageProductPrice = dto.averageProductPrice.toDouble()
        )
}

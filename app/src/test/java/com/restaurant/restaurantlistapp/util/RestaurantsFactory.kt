package com.restaurant.restaurantlistapp.util

import com.restaurant.restaurantlistapp.data.entity.Result
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import com.restaurant.restaurantlistapp.domain.model.Restaurants
import com.restaurant.restaurantlistapp.domain.model.SortDetail
import com.restaurant.restaurantlistapp.domain.model.Status

/**
 * Provides data used for testing
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

object RestaurantsFactory {

    fun getMockRestaurantsDto() = RestaurantsDto(
        restaurants = listOf(getMockRestaurantDto())
    )

    fun getMockRestaurantDto() = RestaurantsDto.RestaurantDto(
        id = "1",
        name = "Castle",
        sortingValues = getMockSortingDto(),
        status = "closed"
    )

    fun getMockSortingDto() = RestaurantsDto.RestaurantDto.SortingValuesDto(
        averageProductPrice = 1,
        bestMatch = 5.0,
        deliveryCosts = 0,
        distance = 0,
        minCost = 3,
        newest = 4.5,
        popularity = 0.0,
        ratingAverage = 0.0
    )

    fun getMockRestaurantResult() = Result.Success(
        Restaurants(
            restaurants = listOf(
                Restaurants.Restaurant(
                    id = 1.0,
                    name = "A",
                    sortingValues = Restaurants.Restaurant.SortingValues(
                        averageProductPrice = 0.0,
                        bestMatch = 0.0,
                        deliveryCosts = 0.0,
                        distance = 0.0,
                        minCost = 0.0,
                        newest = 1.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = Status.OPEN,
                    sortDetail = SortDetail()
                ),
                Restaurants.Restaurant(
                    id = 2.0,
                    name = "B",
                    sortingValues = Restaurants.Restaurant.SortingValues(
                        averageProductPrice = 0.0,
                        bestMatch = 0.0,
                        deliveryCosts = 0.0,
                        distance = 0.0,
                        minCost = 0.0,
                        newest = 2.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = Status.CLOSED,
                    sortDetail = SortDetail()
                ),
                Restaurants.Restaurant(
                    id = 3.0,
                    name = "B",
                    sortingValues = Restaurants.Restaurant.SortingValues(
                        averageProductPrice = 0.0,
                        bestMatch = 0.0,
                        deliveryCosts = 0.0,
                        distance = 0.0,
                        minCost = 0.0,
                        newest = 3.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = Status.ORDER_AHEAD,
                    sortDetail = SortDetail()
                )
            )
        )
    )

    fun getNotSortedMockRestaurantResult() =
        RestaurantsDto(
            restaurants = listOf(
                RestaurantsDto.RestaurantDto(
                    id = "1.0",
                    name = "A",
                    sortingValues = RestaurantsDto.RestaurantDto.SortingValuesDto(
                        averageProductPrice = 0,
                        bestMatch = 0.0,
                        deliveryCosts = 0,
                        distance = 0,
                        minCost = 0,
                        newest = 1.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = "closed"
                ),
                RestaurantsDto.RestaurantDto(
                    id = "2.0",
                    name = "B",
                    sortingValues = RestaurantsDto.RestaurantDto.SortingValuesDto(
                        averageProductPrice = 0,
                        bestMatch = 0.0,
                        deliveryCosts = 0,
                        distance = 0,
                        minCost = 0,
                        newest = 1.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = "order ahead"
                ),
                RestaurantsDto.RestaurantDto(
                    id = "3.0",
                    name = "B",
                    sortingValues = RestaurantsDto.RestaurantDto.SortingValuesDto(
                        averageProductPrice = 0,
                        bestMatch = 0.0,
                        deliveryCosts = 0,
                        distance = 0,
                        minCost = 0,
                        newest = 1.0,
                        popularity = 0.0,
                        ratingAverage = 0.0
                    ),
                    status = "open",
                )
            )
        )

}

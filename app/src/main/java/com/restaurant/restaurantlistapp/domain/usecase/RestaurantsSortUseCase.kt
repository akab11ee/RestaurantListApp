package com.restaurant.restaurantlistapp.domain.usecase


import com.restaurant.restaurantlistapp.data.entity.Result
import com.restaurant.restaurantlistapp.data.entity.data
import com.restaurant.restaurantlistapp.di.qualifier.IoDispatcher
import com.restaurant.restaurantlistapp.domain.model.Restaurants
import com.restaurant.restaurantlistapp.domain.model.SearchSortFilter
import com.restaurant.restaurantlistapp.domain.model.SortByValues
import com.restaurant.restaurantlistapp.domain.model.SortDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Provide method used for sorting and returns sorted data flowable
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

class RestaurantsSortUseCase @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    private var restaurantsResult: Result<Restaurants>? = null
    private var restaurantsData: Restaurants? = null

    private val sortFilterMutableFlow = MutableStateFlow(SearchSortFilter())

    fun setRestaurantsResult(result: Result<Restaurants>) {
        if (restaurantsData == null &&
            result.data?.restaurants?.isNotEmpty() == true
        ) {
            restaurantsData = result.data
        }
        restaurantsResult = result
    }

    fun setQuery(value: String) {
        sortFilterMutableFlow.value = sortFilterMutableFlow.value.copy(filterQuery = value)
    }

    fun setSorting(sorting: SearchSortFilter.Sorting) {
        sortFilterMutableFlow.value = sortFilterMutableFlow.value.copy(sorting = sorting)
    }

    fun sortRestaurants(): Flow<Result<Restaurants>> = flowOf(
        restaurantsData?.let {
            Result.Success(Restaurants(it.restaurants.filter { restaurant ->
                restaurant.name.lowercase()
                    .contains(sortFilterMutableFlow.value.filterQuery.lowercase())
            }.sortedWith(
                compareBy<Restaurants.Restaurant> { restaurant ->
                    if (sortFilterMutableFlow.value.sorting.isSortByStatus) restaurant.status.priority
                    else 1
                }.thenByDescending { restaurant ->
                    when {
                        sortFilterMutableFlow.value.sorting.isSortByBestMatch -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.BEST_MATCH,
                                restaurant.sortingValues.bestMatch
                            )
                            restaurant.sortingValues.bestMatch
                        }
                        sortFilterMutableFlow.value.sorting.isSortByNewest -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.NEWEST,
                                restaurant.sortingValues.newest
                            )
                            restaurant.sortingValues.newest
                        }
                        sortFilterMutableFlow.value.sorting.isSortByRating -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.RATING,
                                restaurant.sortingValues.ratingAverage
                            )
                            restaurant.sortingValues.ratingAverage
                        }
                        sortFilterMutableFlow.value.sorting.isSortByDistance -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.DISTANCE,
                                restaurant.sortingValues.distance
                            )
                            restaurant.sortingValues.distance
                        }
                        sortFilterMutableFlow.value.sorting.isSortByPopularity -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.POPULARITY,
                                restaurant.sortingValues.popularity
                            )
                            restaurant.sortingValues.popularity
                        }
                        sortFilterMutableFlow.value.sorting.isSortByAveragePrice -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.AVERAGE_PRICE,
                                restaurant.sortingValues.averageProductPrice
                            )
                            restaurant.sortingValues.averageProductPrice
                        }
                        sortFilterMutableFlow.value.sorting.isSortByDeliveryCost -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.DELIVERY_COST,
                                restaurant.sortingValues.deliveryCosts
                            )
                            restaurant.sortingValues.deliveryCosts
                        }
                        sortFilterMutableFlow.value.sorting.isSortByMinimumCost -> {
                            restaurant.sortDetail = SortDetail(
                                SortByValues.MINIMUM_PRICE,
                                restaurant.sortingValues.minCost
                            )
                            restaurant.sortingValues.minCost
                        }
                        else -> -restaurant.id
                    }
                }
            )))
        } ?: restaurantsResult ?: Result.Loading)
        .flowOn(ioDispatcher)

    fun getCurrentFilterState() = sortFilterMutableFlow.value.sorting

    fun getSortFilterFlow(): Flow<SearchSortFilter> = sortFilterMutableFlow


}
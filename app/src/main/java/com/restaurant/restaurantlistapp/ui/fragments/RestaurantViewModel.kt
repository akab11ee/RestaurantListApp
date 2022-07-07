package com.restaurant.restaurantlistapp.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurant.restaurantlistapp.data.entity.Result
import com.restaurant.restaurantlistapp.di.qualifier.IoDispatcher
import com.restaurant.restaurantlistapp.domain.model.Restaurants
import com.restaurant.restaurantlistapp.domain.model.SearchSortFilter
import com.restaurant.restaurantlistapp.domain.usecase.RestaurantsFetchUseCase
import com.restaurant.restaurantlistapp.domain.usecase.RestaurantsSortUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Provide state flow of sorted restaurant to be observed by UI
 * Used state flow which requires initialization hence no need for null check
 * Using one API in project(Flow), not two (LiveData and Flow).
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModel @Inject constructor(
    private val restaurantsFetchUseCase: RestaurantsFetchUseCase,
    private val restaurantsSortUseCase: RestaurantsSortUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val refreshState = MutableStateFlow(false)

    val restaurantsFlow: StateFlow<Result<Restaurants>> = channelFlow {
        val fetchRestaurantsFlow: Flow<Result<Restaurants>> =
            restaurantsFetchUseCase
                .fetchRestaurants()

        val sortFilterFlow: Flow<Result<Restaurants>> =
            restaurantsSortUseCase
                .getSortFilterFlow()
                .flatMapLatest {
                    restaurantsSortUseCase.sortRestaurants()
                }

        val refreshFlow: Flow<Result<Restaurants>> =
            refreshState
                .filter { it }
                .flatMapLatest {
                    restaurantsFetchUseCase
                        .fetchRestaurants()
                        .onCompletion {
                            refreshState.tryEmit(false)
                        }
                }

        merge(fetchRestaurantsFlow, sortFilterFlow, refreshFlow)
            .collect {
                restaurantsSortUseCase.setRestaurantsResult(it)
                send(it)
            }
    }.flowOn(ioDispatcher).stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        Result.Loading
    )

    //Provide data sorted by query
    fun setQuery(query: String) = restaurantsSortUseCase.setQuery(query)

    //Provide data sorted by filter
    fun setSorting(sorting: SearchSortFilter.Sorting) = restaurantsSortUseCase.setSorting(sorting)

    fun getCurrentFilterState(): SearchSortFilter.Sorting {
        return restaurantsSortUseCase.getCurrentFilterState()
    }

}
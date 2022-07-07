package com.restaurant.restaurantlistapp.data

import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsDataSource
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsRepository
import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import com.restaurant.restaurantlistapp.di.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Save and fetch data from memory and local and return to [RestaurantsRepoImpl]
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

class RestaurantsRepoImpl @Inject constructor(
    private val restaurantMemoryDataSource: RestaurantsDataSource.Memory,
    private val restaurantLocalDataSource: RestaurantsDataSource.Local,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RestaurantsRepository {
    override suspend fun fetchRestaurants(): Flow<RestaurantsDto> = channelFlow {
        restaurantMemoryDataSource
            .fetchRestaurants()
            ?.let {
                send(it)
                close()
            }

        withContext(ioDispatcher) {
            launch {
                trySend(
                    restaurantLocalDataSource
                        .fetchRestaurants()
                        .also(restaurantMemoryDataSource::cacheInMemory)
                )
            }
        }
    }
}
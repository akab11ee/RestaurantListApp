package com.restaurant.restaurantlistapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsDataSource
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsRepository
import com.restaurant.restaurantlistapp.util.CoroutineTestRule
import com.restaurant.restaurantlistapp.util.RestaurantsFactory.getMockRestaurantsDto
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

@ExperimentalCoroutinesApi
class RestaurantsRepoImplTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var restaurantsLocalDataSource: RestaurantsDataSource.Local

    private lateinit var restaurantsMemoryDataSource: RestaurantsDataSource.Memory

    private lateinit var restaurantsRepository: RestaurantsRepository

    @Before
    fun setUp() {
        restaurantsLocalDataSource = spyk()
        restaurantsMemoryDataSource = spyk()
        restaurantsRepository = RestaurantsRepoImpl(
            restaurantsMemoryDataSource,
            restaurantsLocalDataSource,
            ioDispatcher = coroutineTestRule.testDispatcher
        )
    }

    @Test
    fun `when cache exist then flow emitData from memoryDataSource`() =
        coroutineTestRule.testScope.runTest {
            val cache = getMockRestaurantsDto()
            every {
                restaurantsMemoryDataSource.fetchRestaurants()
            } returns cache


            val job = launch {
                restaurantsRepository.fetchRestaurants().collect {
                    assertEquals(it, cache)
                }
            }

            verify(exactly = 0) {
                runBlocking {
                    restaurantsLocalDataSource.fetchRestaurants()
                }
            }
            job.cancel()
        }

    @Test
    fun `when cache doesn't exist then flow emitData from localDataSource and cacheInMemory`() =
        coroutineTestRule.testScope.runTest {
            val restaurants = getMockRestaurantsDto()
            every {
                runBlocking {
                    restaurantsLocalDataSource.fetchRestaurants()
                }
            } returns restaurants


            val job = launch {
                restaurantsRepository.fetchRestaurants().collect {
                    assertEquals(it, restaurants)
                }
            }

            verify(exactly = 1) {
                restaurantsMemoryDataSource.cacheInMemory(restaurants)
            }


            job.cancel()
        }
}
package com.restaurant.restaurantlistapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.restaurant.restaurantlistapp.data.entity.Result
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsRepository
import com.restaurant.restaurantlistapp.domain.mapper.RestaurantsMapper
import com.restaurant.restaurantlistapp.domain.model.Restaurants
import com.restaurant.restaurantlistapp.util.CoroutineTestRule
import com.restaurant.restaurantlistapp.util.RestaurantsFactory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class RestaurantsFetchUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var restaurantsRepository: RestaurantsRepository

    private lateinit var fetchUseCase: RestaurantsFetchUseCase

    @Before
    fun setUp() {
        restaurantsRepository = mockk(relaxUnitFun = true)
        fetchUseCase = RestaurantsFetchUseCase(
            restaurantsRepository = restaurantsRepository,
            restaurantsMapper = RestaurantsMapper(),
            ioDispatcher = coroutineTestRule.testDispatcher
        )
    }

    @Test
    fun `when fetchRestaurants requested then loading should be propagated`() =
        coroutineTestRule.testScope.runTest {
            val result: Result<Restaurants> = fetchUseCase.fetchRestaurants().first()

            assert(result is Result.Loading)
        }

    @Test
    fun `when fetchRestaurants gets error then it should be observed`() =
        coroutineTestRule.testScope.runTest {

            every {
                runBlocking {
                    restaurantsRepository.fetchRestaurants()
                }
            } throws NullPointerException()

            val result: Result<Restaurants> = fetchUseCase.fetchRestaurants().last()

            assert(
                result is Result.Error &&
                        result.exception is NullPointerException
            )
        }

    @Test
    fun `when fetchRestaurants succeeded then flow should observe domain model`() =
        coroutineTestRule.testScope.runTest {
            every {
                runBlocking {
                    restaurantsRepository.fetchRestaurants()
                }
            } returns flowOf(
                RestaurantsFactory.getMockRestaurantsDto()
            )


            val result: Result<Restaurants> = fetchUseCase.fetchRestaurants().last()

            assert(
                result is Result.Success &&
                        result.data.restaurants.isNotEmpty()
            )
        }
}
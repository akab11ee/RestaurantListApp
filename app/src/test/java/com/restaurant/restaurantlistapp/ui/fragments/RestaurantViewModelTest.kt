package com.restaurant.restaurantlistapp.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.restaurant.restaurantlistapp.domain.usecase.RestaurantsFetchUseCase
import com.restaurant.restaurantlistapp.domain.usecase.RestaurantsSortUseCase
import com.restaurant.restaurantlistapp.util.CoroutineTestRule
import com.restaurant.restaurantlistapp.util.RestaurantsFactory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RestaurantViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var restaurantsFetchUseCase: RestaurantsFetchUseCase
    private lateinit var restaurantsSortUseCase: RestaurantsSortUseCase
    private lateinit var viewModel: RestaurantViewModel

    @Before
    fun setUp() {
        restaurantsFetchUseCase = mockk(relaxUnitFun = true)
        restaurantsSortUseCase = mockk(relaxUnitFun = true)
        viewModel = RestaurantViewModel(
            restaurantsFetchUseCase,
            restaurantsSortUseCase,
            ioDispatcher = coroutineTestRule.testDispatcher
        )
    }

    @Test
    fun `when flow updated then result should contains value`() =
        coroutineTestRule.testScope.runTest {
            val data = RestaurantsFactory.getMockRestaurantResult()

            every {
                restaurantsSortUseCase.getSortFilterFlow()
            }.returns(emptyFlow())

            every {
                runBlocking {
                    restaurantsFetchUseCase.fetchRestaurants()
                }
            } returns flowOf(data)


            val job = launch {
                viewModel.restaurantsFlow.last().also {
                    assertEquals(data, it)
                }
            }
            job.cancel()
        }
}

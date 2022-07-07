package com.restaurant.restaurantlistapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.restaurant.restaurantlistapp.data.entity.Result
import com.restaurant.restaurantlistapp.data.entity.data
import com.restaurant.restaurantlistapp.domain.model.Restaurants
import com.restaurant.restaurantlistapp.domain.model.SearchSortFilter
import com.restaurant.restaurantlistapp.util.CoroutineTestRule
import com.restaurant.restaurantlistapp.util.RestaurantsFactory.getMockRestaurantResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantsSortUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sortUseCase: RestaurantsSortUseCase

    @Before
    fun setUp() {
        sortUseCase = RestaurantsSortUseCase(coroutineTestRule.testDispatcher)
    }

    @Test
    fun `when setQuery called then flow should reflect updated value`() =
        coroutineTestRule.testScope.runTest {
            val value = "Restaurant"

            sortUseCase.setQuery(value)

            val job = launch {
                sortUseCase.getSortFilterFlow().collect {
                    assertEquals(
                        it.filterQuery,
                        value
                    )
                }
            }

            job.cancel()
        }

    @Test
    fun `when setSorting called then flow should reflect updated value`() =
        coroutineTestRule.testScope.runTest {
            val value = SearchSortFilter.Sorting(true)

            sortUseCase.setSorting(value)

            val job = launch {
                sortUseCase.getSortFilterFlow().collectLatest {
                    assertEquals(
                        it.sorting,
                        value
                    )
                }
            }

            job.cancel()
        }

    // filter name with A
    @Test
    fun `when sortRestaurants called with query then flow should return only filtered items`() =
        coroutineTestRule.testScope.runTest {
            val query = "A"
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 1)
                assert(restaurants.first().name == query)
            }
        }


    // sort by opening status
    @Test
    fun `when sortRestaurants called with sortByStatus then flow should return sorted by status priority`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setSorting(SearchSortFilter.Sorting(true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assertThat(restaurants)
                    .isSortedAccordingTo(compareBy { restaurant ->
                        restaurant.status.priority
                    })
            }
        }


    // filter and sort with open status
    @Test
    fun `when filter and status combined then flow should return filtered and sorted list by status priority`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()
            val query = "B"

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)
            sortUseCase.setSorting(SearchSortFilter.Sorting(true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 2)

                assertThat(restaurants)
                    .isSortedAccordingTo(compareBy { restaurant ->
                        restaurant.status.priority
                    })
            }
        }

    // filter and sort with sortingValue(newest)
    @Test
    fun `when filter and sortingValue then flow should return filtered and sorted list by given sortingValue`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()
            val query = "B"

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)
            sortUseCase.setSorting(SearchSortFilter.Sorting(isSortByNewest = true))

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 2)

                assertThat(restaurants)
                    .isSortedAccordingTo(compareByDescending { restaurant ->
                        restaurant.sortingValues.newest
                    })
            }
        }

    // heaven (filter - open state sorting - newest sorting)
    @Test
    fun `when all possibilities combined then flow should return filtered and sorted list`() =
        coroutineTestRule.testScope.runTest {
            val restaurantsResult: Result.Success<Restaurants> = getMockRestaurantResult()
            val query = "B"

            sortUseCase.setRestaurantsResult(restaurantsResult)
            sortUseCase.setQuery(query)
            sortUseCase.setSorting(
                SearchSortFilter.Sorting(
                    isSortByStatus = true,
                    isSortByNewest = true
                )
            )

            sortUseCase.sortRestaurants().collectLatest {
                val restaurants = it.data?.restaurants ?: fail("restaurants are null")

                assert(restaurants.size == 2)

                assertThat(restaurants)
                    .isSortedAccordingTo(compareBy { restaurant ->
                        restaurant.status.priority
                    })

                assertThat(restaurants)
                    .isSortedAccordingTo(compareByDescending { restaurant ->
                        restaurant.sortingValues.newest
                    })
            }
        }

    @Test
    fun `when restaurants has not set yet flow should return Loading`() =
        coroutineTestRule.testScope.runTest {
            sortUseCase.setSorting(SearchSortFilter.Sorting(true))

            sortUseCase.sortRestaurants().collectLatest {
                assert(it is Result.Loading)
            }
        }
}
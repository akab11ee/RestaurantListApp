package com.restaurant.restaurantlistapp.domain.mapper

import com.restaurant.restaurantlistapp.data.local.model.RestaurantsDto
import com.restaurant.restaurantlistapp.domain.model.Status
import com.restaurant.restaurantlistapp.util.RestaurantsFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

class RestaurantsMapperTest {

    private val mapper by lazy(::RestaurantsMapper)

    @Test
    fun `mapToRestaurants should map dto to domain model`() {
        val dto = RestaurantsFactory.getMockRestaurantsDto()

        val restaurants = mapper.mapToRestaurants(dto)

        assert(restaurants.restaurants.isNotEmpty())
    }

    @Test
    fun `mapToRestaurants should map null restaurants to empty domain model`() {
        val dto = RestaurantsDto(null)

        val restaurants = mapper.mapToRestaurants(dto)

        assert(restaurants.restaurants.isEmpty())
    }

    @Test
    fun `mapToRestaurant should map dto to domain model`() {
        val dto = RestaurantsFactory.getMockRestaurantDto()

        val restaurant = mapper.mapToRestaurant(dto)

        assertEquals(
            dto.status,
            restaurant.status.title.lowercase()
        )

        assertEquals(
            dto.id.toDouble(),
            restaurant.id,
            0.0
        )

        assertEquals(
            dto.name,
            restaurant.name
        )
    }

    @Test
    fun `mapToStatus should map known status to status enum`() {
        //given
        val givenStatus = "open"

        //when
        val status = mapper.mapToStatus(givenStatus)

        //then
        assertEquals(
            status,
            Status.OPEN
        )
    }

    @Test
    fun `mapToStatus should map null status to default(CLOSED) status`() {
        //given
        val givenStatus = null

        //when
        val status = mapper.mapToStatus(givenStatus)

        //then
        assertEquals(
            status,
            Status.CLOSED
        )
    }

    @Test
    fun `mapToStatus should map new status type to default(CLOSED) status`() {
        //given
        val givenStatus = "o p e n"

        //when
        val status = mapper.mapToStatus(givenStatus)

        //then
        assertEquals(
            status,
            Status.CLOSED
        )
    }

    @Test
    fun `mapToSortingValue should map dto to domain model`() {
        //given
        val dto = RestaurantsFactory.getMockSortingDto()

        //when
        val sortingValue = mapper.mapToSortingValue(dto)

        //then
        assertEquals(
            dto.bestMatch,
            sortingValue.bestMatch,
            0.0
        )

        assertEquals(
            dto.deliveryCosts,
            sortingValue.deliveryCosts.toInt()
        )

        assertEquals(
            dto.distance,
            sortingValue.distance.toInt()
        )

        assertEquals(
            dto.minCost,
            sortingValue.minCost.toInt()
        )

        assertEquals(
            dto.newest,
            sortingValue.newest,
            0.0
        )

        assertEquals(
            dto.popularity,
            sortingValue.popularity,
            0.0
        )

        assertEquals(
            dto.ratingAverage,
            sortingValue.ratingAverage,
            0.0
        )

        assertEquals(
            dto.averageProductPrice,
            sortingValue.averageProductPrice.toInt()
        )
    }

    @Test
    fun `mapToSortingValue should return restaurant with open status and best match on top`() {
        //given
        val dto = RestaurantsFactory.getNotSortedMockRestaurantResult()

        //when
        val sortingValue = mapper.mapToRestaurants(dto)

        //then
        //Restaurant id 3 have status as open and highest best match

        assertEquals(sortingValue.restaurants[0].id.toInt(), 3)
    }
}
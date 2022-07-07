package com.restaurant.restaurantlistapp.domain.model

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

enum class Status(val title: String, val priority: Double) {
    OPEN("Open", 1.0),
    ORDER_AHEAD("Order ahead", 2.0),
    CLOSED("Closed", 3.0)
}

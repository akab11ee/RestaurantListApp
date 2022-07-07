package com.restaurant.restaurantlistapp.di.qualifier

import javax.inject.Qualifier

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

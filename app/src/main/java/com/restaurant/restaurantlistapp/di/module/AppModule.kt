package com.restaurant.restaurantlistapp.di.module

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.restaurant.restaurantlistapp.BuildConfig
import com.restaurant.restaurantlistapp.data.RestaurantsRepoImpl
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsDataSource
import com.restaurant.restaurantlistapp.data.interfaces.RestaurantsRepository
import com.restaurant.restaurantlistapp.data.local.RestaurantLocalDataSource
import com.restaurant.restaurantlistapp.data.local.RestaurantsProvider
import com.restaurant.restaurantlistapp.data.memory.RestaurantMemoryDataSource
import com.restaurant.restaurantlistapp.di.qualifier.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

/**
 * Provides application dependencies
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree = object : Timber.DebugTree() {
        override fun isLoggable(tag: String?, priority: Int) =
         BuildConfig.DEBUG || priority >= Log.INFO
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideAssetManager(
        @ApplicationContext context: Context
    ): AssetManager = context.assets

    @Provides
    @Singleton
    fun provideLocalDataSource(restaurantsProvider: RestaurantsProvider): RestaurantsDataSource.Local =
        RestaurantLocalDataSource(restaurantsProvider)

    @Provides
    @Singleton
    fun provideMemoryDataSource(): RestaurantsDataSource.Memory =
        RestaurantMemoryDataSource()

    @Provides
    @Singleton
    fun provideRepository(
        restaurantMemoryDataSource: RestaurantsDataSource.Memory,
        restaurantLocalDataSource: RestaurantsDataSource.Local,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): RestaurantsRepository =
        RestaurantsRepoImpl(restaurantMemoryDataSource, restaurantLocalDataSource, ioDispatcher)

}

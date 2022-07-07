package com.restaurant.restaurantlistapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.restaurant.restaurantlistapp.R
import com.restaurant.restaurantlistapp.databinding.ActivityRestaurantBinding
import com.restaurant.restaurantlistapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_restaurant.*

@AndroidEntryPoint
class RestaurantListActivity :
    BaseActivity<ActivityRestaurantBinding, RestaurantActivityViewModel>() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override val viewModel: RestaurantActivityViewModel by viewModels()

    override fun getViewBinding(): ActivityRestaurantBinding {
        return ActivityRestaurantBinding.inflate(layoutInflater)
    }
}
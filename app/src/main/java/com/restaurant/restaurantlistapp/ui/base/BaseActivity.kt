package com.restaurant.restaurantlistapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Common method for all activities can be put here
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel?> :
    AppCompatActivity() {

    abstract val viewModel: VM
    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    private val binding: VB
        get() = _binding as VB

    //Initialize UI and listeners
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
    }

}
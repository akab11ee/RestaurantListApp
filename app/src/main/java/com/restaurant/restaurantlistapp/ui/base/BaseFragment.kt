package com.restaurant.restaurantlistapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Common method for all fragment can be put here
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment(),
    LifecycleObserver {

    abstract val viewModel: VM

    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

}
package com.restaurant.restaurantlistapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Common method for all bottom sheet dialog can be put here
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

abstract class BaseBottomSheetDialog<VB : ViewBinding> : BottomSheetDialogFragment(),
    LifecycleObserver {

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
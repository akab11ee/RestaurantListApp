package com.restaurant.restaurantlistapp.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.navArgs
import com.restaurant.restaurantlistapp.databinding.DialogFilterBinding
import com.restaurant.restaurantlistapp.domain.model.SearchSortFilter
import com.restaurant.restaurantlistapp.ui.base.BaseBottomSheetDialog
import com.restaurant.restaurantlistapp.utils.AppConstant
import kotlinx.android.synthetic.main.dialog_filter.*


/**
 * Filter dialog UI
 *
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

class FilterBottomSheetDialog : BaseBottomSheetDialog<DialogFilterBinding>(), View.OnClickListener {

    // Navigation
    private val args: FilterBottomSheetDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            item = args.sortingValue
        }
        initOnClickListener()
    }

    private fun initOnClickListener() {
        btn_best_match.setOnClickListener(this)
        btn_newest.setOnClickListener(this)
        btn_rating_average.setOnClickListener(this)
        btn_distance.setOnClickListener(this)

        btn_popularity.setOnClickListener(this)
        btn_product_price.setOnClickListener(this)
        btn_delivery_cost.setOnClickListener(this)
        btn_minimum_cost.setOnClickListener(this)
    }

    private fun sortListData(sorting: SearchSortFilter.Sorting) {
        val navController = findNavController(this)
        navController.previousBackStackEntry?.savedStateHandle?.set(
            AppConstant.SORTING_KEY,
            sorting
        )
        dismiss()
    }

    override fun getViewBinding(): DialogFilterBinding {
        return DialogFilterBinding.inflate(layoutInflater)
    }

    //Returns the required sorting value on click of button
    override fun onClick(view: View?) {
        when (view?.id) {
            btn_best_match.id -> sortListData(SearchSortFilter.Sorting(isSortByBestMatch = true))
            btn_newest.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByNewest = true
                )
            )
            btn_rating_average.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByRating = true
                )
            )
            btn_distance.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByDistance = true
                )
            )
            btn_popularity.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByPopularity = true
                )
            )
            btn_product_price.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByAveragePrice = true
                )
            )
            btn_delivery_cost.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByDeliveryCost = true
                )
            )
            btn_minimum_cost.id -> sortListData(
                SearchSortFilter.Sorting(
                    isSortByBestMatch = false,
                    isSortByMinimumCost = true
                )
            )
            else -> sortListData(SearchSortFilter.Sorting())
        }
    }

}
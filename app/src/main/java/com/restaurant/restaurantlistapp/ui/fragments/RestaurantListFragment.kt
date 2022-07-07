package com.restaurant.restaurantlistapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurant.restaurantlistapp.data.entity.Result
import com.restaurant.restaurantlistapp.databinding.FragmentRestaurantBinding
import com.restaurant.restaurantlistapp.domain.model.SearchSortFilter
import com.restaurant.restaurantlistapp.ui.base.BaseFragment
import com.restaurant.restaurantlistapp.ui.fragments.adapter.RestaurantListAdapter
import com.restaurant.restaurantlistapp.utils.AppConstant
import com.restaurant.restaurantlistapp.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_restaurant.*
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class RestaurantListFragment : BaseFragment<FragmentRestaurantBinding, RestaurantViewModel>() {
    override val viewModel: RestaurantViewModel by viewModels()

    private lateinit var listAdapter: RestaurantListAdapter

    override fun getViewBinding(): FragmentRestaurantBinding =
        FragmentRestaurantBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initFilterClick()
        observeSortingCallBacks()
        /*viewModel.setSorting(
            SearchSortFilter.Sorting(
                isSortByBestMatch = true
            )
        )*/
        getRestaurantData()
    }

    private fun initFilterClick() {
        ic_filter.setOnClickListener {
            val action =
                RestaurantListFragmentDirections.actionSearch(viewModel.getCurrentFilterState())
            navigate(action)
        }
    }

    private fun initUI() {
        initRecyclerView()
        initEditTextListener()
    }

    private fun initEditTextListener() {
        edit_text_search.doOnTextChanged { text, _, _, _ ->
            val queryValue = text.toString().lowercase(Locale.getDefault())
            viewModel.setQuery(queryValue)
        }
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        listAdapter = RestaurantListAdapter()
        recycler_view.adapter = listAdapter
    }

    private fun getRestaurantData() {
        lifecycleScope.launchWhenStarted {
            viewModel.restaurantsFlow.collect {
                when (it) {
                    is Result.Loading -> {
                        binding.apply {
                            isLoading = true
                        }
                    }
                    is Result.Error -> {
                        binding.apply {
                            isLoading = false
                            errorValue = it.exception.localizedMessage
                        }
                    }
                    is Result.Success -> {
                        binding.apply {
                            isLoading = false
                            noDataPresent = it.data.restaurants.isNullOrEmpty()
                        }
                        listAdapter.setData(it.data.restaurants)
                    }
                }

            }

        }

    }

    private fun observeSortingCallBacks() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<SearchSortFilter.Sorting>(
            AppConstant.SORTING_KEY
        )?.observe(viewLifecycleOwner) {
            viewModel.setSorting(it)
        }
    }
}
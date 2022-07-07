package com.restaurant.restaurantlistapp.utils.view

import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.restaurant.restaurantlistapp.R

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

//Used to set selected and un selected filter buttons color
@BindingAdapter("set_background_filter")
fun setFilterButtonBackground(view: AppCompatButton, isSelected: Boolean?) {
    if (isSelected == true) {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorPrimaryDark))
    } else {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorPrimaryLight))
    }
}

//Used to set text color of restaurant opening status
@BindingAdapter("set_text_color")
fun setTextColor(view: TextView, priority: Double?) {
    when (priority) {
        1.0 -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.colorGreen))
        }
        2.0 -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.colorOrange))
        }
        3.0 -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.colorRed))
        }
        else -> {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.colorGreen))
        }
    }
}
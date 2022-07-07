package com.restaurant.restaurantlistapp.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @Author: Akash Abhishek
 * @Date: 07 July 2022
 */

data class SearchSortFilter(
    val sorting: Sorting = Sorting(),
    val filterQuery: String = "",
) {
    data class Sorting(
        val isSortByStatus: Boolean = true,
        val isSortByBestMatch: Boolean = true,
        val isSortByNewest: Boolean = false,
        val isSortByRating: Boolean = false,
        val isSortByDistance: Boolean = false,
        val isSortByPopularity: Boolean = false,
        val isSortByAveragePrice: Boolean = false,
        val isSortByDeliveryCost: Boolean = false,
        val isSortByMinimumCost: Boolean = false,
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeByte(if (isSortByStatus) 1 else 0)
            parcel.writeByte(if (isSortByBestMatch) 1 else 0)
            parcel.writeByte(if (isSortByNewest) 1 else 0)
            parcel.writeByte(if (isSortByRating) 1 else 0)
            parcel.writeByte(if (isSortByDistance) 1 else 0)
            parcel.writeByte(if (isSortByPopularity) 1 else 0)
            parcel.writeByte(if (isSortByAveragePrice) 1 else 0)
            parcel.writeByte(if (isSortByDeliveryCost) 1 else 0)
            parcel.writeByte(if (isSortByMinimumCost) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Sorting> {
            override fun createFromParcel(parcel: Parcel): Sorting {
                return Sorting(parcel)
            }

            override fun newArray(size: Int): Array<Sorting?> {
                return arrayOfNulls(size)
            }
        }
    }
}

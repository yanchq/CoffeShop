package com.example.coffeshop.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Int = UNDEFINED_ID,
    val name: String,
    val cost: Double,
    val category: String,
    val size: String = MEDIUM,
    val sugar: String = MEDIUM,
    val syrup: String = NO_SYRUP,
    val count: Int = 1,
    val image: String
): Parcelable {

    companion object {
        const val UNDEFINED_ID = 0
        const val MEDIUM = "Medium"
        const val NO_SYRUP = "None"
    }
}

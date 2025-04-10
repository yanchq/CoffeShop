package com.example.coffeshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.coffeshop.domain.entity.Item

@Entity(tableName = "order_items")
data class OrderItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val cost: Double,
    val category: String,
    val size: String = Item.MEDIUM,
    val sugar: String = Item.MEDIUM,
    val syrup: String = Item.MEDIUM,
    val count: Int = 1,
    val image: String
)

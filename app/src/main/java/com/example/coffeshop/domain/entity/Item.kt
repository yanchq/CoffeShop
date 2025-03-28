package com.example.coffeshop.domain.entity

data class Item(
    val name: String,
    val cost: Double,
    var volume: Double = 0.3,
    val category: String,
    val image: String
)

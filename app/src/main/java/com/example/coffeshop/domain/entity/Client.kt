package com.example.coffeshop.domain.entity

data class Client(
    val name: String,
    val email: String,
    val orders: List<List<Item>> = listOf<List<Item>>()
)

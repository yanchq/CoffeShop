package com.example.coffeshop.domain.entity

data class Order(
    val listItem: List<Item>,
    val status: String = IN_PROGRESS
) {
    companion object {
        const val IN_PROGRESS = "In Progress"
    }
}
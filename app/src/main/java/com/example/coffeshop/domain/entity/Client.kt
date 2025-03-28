package com.example.coffeshop.domain.entity

import javax.inject.Inject

data class Client(
    val id: Long = 0,
    val name: String,
    val email: String
)

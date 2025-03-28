package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository

class IsLoggedUseCase(private val repository: CoffeeShopRepository) {

    suspend fun invoke(): Long {
        return repository.isLogged()
    }
}
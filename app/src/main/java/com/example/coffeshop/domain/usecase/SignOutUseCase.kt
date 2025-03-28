package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository

class SignOutUseCase(private val repository: CoffeeShopRepository) {

    suspend fun invoke() {
        repository.signOut()
    }
}
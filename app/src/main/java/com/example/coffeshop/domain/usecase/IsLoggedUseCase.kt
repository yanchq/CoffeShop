package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class IsLoggedUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(): Boolean {
        return repository.isLogged()
    }
}
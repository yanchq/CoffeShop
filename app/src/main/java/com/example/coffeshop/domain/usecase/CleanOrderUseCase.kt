package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class CleanOrderUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke() {
        repository.cleanOrder()
    }
}
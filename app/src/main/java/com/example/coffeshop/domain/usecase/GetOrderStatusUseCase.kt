package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderStatusUseCase @Inject constructor(
    private val repository: CoffeeShopRepository
) {

    suspend fun invoke(orderId: Int): Flow<String> {
        return repository.getOrderStatusUseCase(orderId)
    }
}
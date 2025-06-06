package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentOrderUseCase @Inject constructor(
    private val repository: CoffeeShopRepository
) {
    fun invoke(): Flow<List<Item>> {
        return repository.getCurrentOrder()
    }
}
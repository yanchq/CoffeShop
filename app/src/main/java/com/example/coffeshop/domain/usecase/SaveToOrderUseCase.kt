package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import com.example.coffeshop.domain.entity.Item
import javax.inject.Inject

class SaveToOrderUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(item: Item) {
        repository.saveToOrder(item)
    }
}
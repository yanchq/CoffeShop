package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class LoadItemListUseCase @Inject constructor(
    private val repository: CoffeeShopRepository
) {
    suspend fun invoke(): List<Any> {
        return repository.loadItemList()
    }
}
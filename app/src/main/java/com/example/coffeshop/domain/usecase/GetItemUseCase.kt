package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    fun invoke(itemId: Int): Item {
        return repository.getItem(itemId)
    }
}
package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class GetListItemUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(): List<Any> {
        return repository.getListItem()
    }
}
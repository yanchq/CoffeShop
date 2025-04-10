package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class DeleteFromOrderUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(itemId: Int){
        repository.deleteFromOrder(itemId)
    }
}
package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(
        listItem: List<Item>,
        onSuccessCallback: (Int) -> Unit
    ) {
        repository.placeOrder(listItem, onSuccessCallback)
    }
}
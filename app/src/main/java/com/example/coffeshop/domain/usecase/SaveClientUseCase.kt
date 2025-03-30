package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import com.example.coffeshop.domain.entity.Client
import javax.inject.Inject

class SaveClientUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(
        client: Client, password: String,
        weakPasswordCallback: () -> Unit,
        userExistsCallback: () -> Unit,
        successCallback: () -> Unit
    ) {

        repository.saveClient(
            client, password,
            weakPasswordCallback, userExistsCallback, successCallback
        )
    }
}
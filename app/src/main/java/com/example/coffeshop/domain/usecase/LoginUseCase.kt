package com.example.coffeshop.domain.usecase

import com.example.coffeshop.domain.repository.CoffeeShopRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: CoffeeShopRepository) {

    suspend fun invoke(email: String, password: String, successCallback: () -> Unit) {
        repository.login(email, password, successCallback)
    }
}
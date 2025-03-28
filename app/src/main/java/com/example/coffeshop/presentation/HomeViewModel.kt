package com.example.coffeshop.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.usecase.SignOutUseCase

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private var repository = CoffeeShopRepositoryImpl(application)

    private val unloggedUseCase = SignOutUseCase(repository)

    suspend fun unlogged() {
        unloggedUseCase.invoke()
    }
}
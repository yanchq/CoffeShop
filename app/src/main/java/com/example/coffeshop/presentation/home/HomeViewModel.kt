package com.example.coffeshop.presentation.home

import androidx.lifecycle.ViewModel
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CoffeeShopRepositoryImpl
): ViewModel() {

    private val unloggedUseCase = SignOutUseCase(repository)

    suspend fun unlogged() {
        unloggedUseCase.invoke()
    }
}
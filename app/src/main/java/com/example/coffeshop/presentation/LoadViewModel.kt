package com.example.coffeshop.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.usecase.IsLoggedUseCase
import kotlinx.coroutines.launch

class LoadViewModel(application: Application): AndroidViewModel(application)  {

    private val repository = CoffeeShopRepositoryImpl(application)
    private val isLoggedUseCase = IsLoggedUseCase(repository)

    private var _loggedId = MutableLiveData<Long>()
    val loggedId: MutableLiveData<Long>
        get() = _loggedId

    fun getLoggedId() {
        viewModelScope.launch {
            val id = isLoggedUseCase.invoke()
            _loggedId.postValue(id)
        }
    }
}
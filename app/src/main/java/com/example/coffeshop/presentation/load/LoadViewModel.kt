package com.example.coffeshop.presentation.load

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.usecase.IsLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadViewModel @Inject constructor(
    private val repository: CoffeeShopRepositoryImpl
): ViewModel()  {

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
package com.example.coffeshop.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.usecase.DeleteFromOrderUseCase
import com.example.coffeshop.domain.usecase.GetCurrentOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getCurrentOrderUseCase: GetCurrentOrderUseCase,
    private val deleteFromOrderUseCase: DeleteFromOrderUseCase
): ViewModel() {

    private val _currentOrder = MutableLiveData<List<Item>>()
    val currentOrder: LiveData<List<Item>>
        get() = _currentOrder

    fun getCurrentOrder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val order = getCurrentOrderUseCase.invoke()
                _currentOrder.postValue(order)
            }
        }
    }

    fun deleteFromOrder(itemId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteFromOrderUseCase.invoke(itemId)
            }
            getCurrentOrder()
        }
    }
}
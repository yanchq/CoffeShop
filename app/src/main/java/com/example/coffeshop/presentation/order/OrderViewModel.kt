package com.example.coffeshop.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.usecase.CleanOrderUseCase
import com.example.coffeshop.domain.usecase.DeleteFromOrderUseCase
import com.example.coffeshop.domain.usecase.GetCurrentOrderUseCase
import com.example.coffeshop.domain.usecase.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getCurrentOrderUseCase: GetCurrentOrderUseCase,
    private val deleteFromOrderUseCase: DeleteFromOrderUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val cleanOrderUseCase: CleanOrderUseCase
): ViewModel() {

    private val _currentOrder = MutableStateFlow<List<Item>>(emptyList())
    val currentOrder: StateFlow<List<Item>>
        get() = _currentOrder

    init {
        viewModelScope.launch {
            getCurrentOrderUseCase.invoke().collect {
                _currentOrder.value = it
            }
        }
    }

    fun deleteFromOrder(itemId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteFromOrderUseCase.invoke(itemId)
            }
        }
    }

    fun placeOrder(startService: (Int) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val callback = cleanOrderUseCase.invoke()
                placeOrderUseCase.invoke(currentOrder.value) { orderId ->
                    callback
                    startService(orderId)
                }
            }
        }
    }
}
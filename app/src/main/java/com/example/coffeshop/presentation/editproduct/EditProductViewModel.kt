package com.example.coffeshop.presentation.editproduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.usecase.GetItemUseCase
import com.example.coffeshop.domain.usecase.SaveToOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val saveToOrderUseCase: SaveToOrderUseCase,
    private val getItemUseCase: GetItemUseCase
): ViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item>
        get() = _item

    fun saveToOrder(item: Item) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveToOrderUseCase.invoke(item)
            }
        }
    }

    fun getItem(itemId: Int) {
        val item = getItemUseCase.invoke(itemId)
        Log.d("ItemTest", item.toString())
        _item.value = item
    }
}
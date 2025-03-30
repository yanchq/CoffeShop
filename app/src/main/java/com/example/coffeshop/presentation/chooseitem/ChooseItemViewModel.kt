package com.example.coffeshop.presentation.chooseitem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.usecase.GetListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseItemViewModel @Inject constructor(
    private val getListItemUseCase: GetListItemUseCase
) : ViewModel() {

    init {
        getListItem()
    }

    private val _listItem = MutableLiveData<List<Any>>()
    val listItem: LiveData<List<Any>>
        get() = _listItem

    private fun getListItem() {
        viewModelScope.launch {
            _listItem.postValue(getListItemUseCase.invoke())
        }
    }

    fun getListCategory(): List<String> {
        return listItem.value?.filter { it is String } as List<String>
    }
}
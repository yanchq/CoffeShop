package com.example.coffeshop.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.usecase.GetListItemUseCase

class ChooseItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CoffeeShopRepositoryImpl(application)
    private val getListItemUseCase = GetListItemUseCase(repository)

    private val _listItem = MutableLiveData<List<Item>>()
    val listItem: LiveData<List<Item>>
        get() = _listItem

    suspend fun getListItem() {
        _listItem.postValue(getListItemUseCase.invoke())
    }
}
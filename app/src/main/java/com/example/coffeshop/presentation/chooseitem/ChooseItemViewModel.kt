package com.example.coffeshop.presentation.chooseitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.usecase.GetListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseItemViewModel @Inject constructor(
    private val repository: CoffeeShopRepositoryImpl
): ViewModel() {

    private val getListItemUseCase = GetListItemUseCase(repository)

    private val _listItem = MutableLiveData<List<Item>>()
    val listItem: LiveData<List<Item>>
        get() = _listItem

    suspend fun getListItem() {
        _listItem.postValue(getListItemUseCase.invoke())
    }
}
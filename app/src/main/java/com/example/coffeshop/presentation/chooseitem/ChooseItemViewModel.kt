package com.example.coffeshop.presentation.chooseitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeshop.domain.usecase.GetListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseItemViewModel @Inject constructor(
    private val getListItemUseCase: GetListItemUseCase
) : ViewModel() {

    private val _listItem = MutableLiveData<List<Any>>()
    val listItem: LiveData<List<Any>>
        get() = _listItem

    fun getListItem() {
            _listItem.value = getListItemUseCase.invoke()
    }

    fun getListCategory(): List<String> {
        return listItem.value?.filter { it is String } as List<String>
    }
}
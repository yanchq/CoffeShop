package com.example.coffeshop.presentation.load

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeshop.domain.usecase.IsLoggedUseCase
import com.example.coffeshop.domain.usecase.LoadItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadViewModel @Inject constructor(
    private val isLoggedUseCase: IsLoggedUseCase,
    private val loadItemListUseCase: LoadItemListUseCase
): ViewModel()  {

    private var _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean>
        get() = _isLogged

    private var _listItem = MutableLiveData<List<Any>>()
    val listItem: LiveData<List<Any>>
        get() = _listItem

    fun getLoggedId() {
        viewModelScope.launch {
                val logged = isLoggedUseCase.invoke()
                _isLogged.postValue(logged)
        }
    }

    fun getListItem() {
        viewModelScope.launch {
                val list = loadItemListUseCase.invoke()
                _listItem.postValue(list)
        }
    }
}
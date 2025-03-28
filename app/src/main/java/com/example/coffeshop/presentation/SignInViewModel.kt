package com.example.coffeshop.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.usecase.LoginUseCase

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoffeeShopRepositoryImpl(application)
    private val loginUseCase = LoginUseCase(repository)

    private val _weakPasswordFlag = MutableLiveData<Boolean>()
    val weakPasswordFlag: LiveData<Boolean>
        get() = _weakPasswordFlag

    private val _badEmailFlag = MutableLiveData<Int>()
    val badEmailFlag: LiveData<Int>
        get() = _badEmailFlag

    suspend fun login(email: String, password: String, successCallback: () -> Unit) {
        if (validate(email, password)) {
            loginUseCase.invoke(email, password, successCallback)
        }
    }

    private fun validate(email: String, password: String): Boolean {
        var validate = true

        if (email.isBlank() ||
            !Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matches(email)
        ) {
            _badEmailFlag.value = 1
            validate = false
        }
        if (password.isBlank()) {
            _weakPasswordFlag.value = true
            validate = false
        }

        return validate
    }
}
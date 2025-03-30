package com.example.coffeshop.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.entity.Client
import com.example.coffeshop.domain.usecase.SaveClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: CoffeeShopRepositoryImpl
): ViewModel() {

    private val saveClientUseCase = SaveClientUseCase(repository)

    private val _weakPasswordFlag = MutableLiveData<Boolean>()
    val weakPasswordFlag: LiveData<Boolean>
        get() = _weakPasswordFlag

    private val _badUsernameFlag = MutableLiveData<Boolean>()
    val badUsernameFlag: LiveData<Boolean>
        get() = _badUsernameFlag

    private val _badEmailFlag = MutableLiveData<Int>()
    val badEmailFlag: LiveData<Int>
        get() = _badEmailFlag

    suspend fun saveClient(client: Client, password: String, successCallback: () -> Unit) {

        val weakPasswordCallback = {
            _weakPasswordFlag.postValue(true)
        }
        val userExistsCallback = {
            _badEmailFlag.postValue(2)
        }

        if (validate(client.name, client.email, password)) {
            saveClientUseCase.invoke(
                client, password,
                weakPasswordCallback, userExistsCallback, successCallback
            )
        }
    }


    private fun validate(name: String, email: String, password: String): Boolean {
        var validate = true

        if (name.isBlank()) {
            _badUsernameFlag.value = true
            validate = false
        }
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
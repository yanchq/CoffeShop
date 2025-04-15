package com.example.coffeshop.domain.repository

import com.example.coffeshop.domain.entity.Client
import com.example.coffeshop.domain.entity.Item
import kotlinx.coroutines.flow.Flow

interface CoffeeShopRepository {

    suspend fun saveClient(
        client: Client, password: String,
        weakPasswordCallback: () -> Unit,
        userExistsCallback: () -> Unit,
        successCallback: () -> Unit
    )

    suspend fun saveToOrder(item: Item)

    suspend fun isLogged(): Boolean

    suspend fun signOut()

    suspend fun login(email: String, password: String, successCallback: () -> Unit)

    fun getListItem(): List<Any>

    fun getItem(itemId: Int): Item

    suspend fun deleteFromOrder(itemId: Int)

    suspend fun cleanOrder()

    fun getCurrentOrder(): Flow<List<Item>>

    suspend fun loadItemList(): List<Any>

    suspend fun placeOrder(
        listItem: List<Item>,
        onSuccessCallback: (Int) -> Unit
    )

    suspend fun getOrderStatusUseCase(orderId: Int): Flow<String>
}
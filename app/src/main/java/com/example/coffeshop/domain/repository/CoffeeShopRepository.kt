package com.example.coffeshop.domain.repository

import com.example.coffeshop.domain.entity.Client
import com.example.coffeshop.domain.entity.Item

interface CoffeeShopRepository {

    suspend fun saveClient(
        client: Client, password: String,
        weakPasswordCallback: () -> Unit,
        userExistsCallback: () -> Unit,
        successCallback: () -> Unit
    )

    suspend fun saveToOrder(item: Item)

    suspend fun isLogged(): Long

    suspend fun signOut()

    suspend fun login(email: String, password: String, successCallback: () -> Unit)

    fun getListItem(): List<Any>

    fun getItem(itemId: Int): Item

    suspend fun deleteFromOrder(itemId: Int)

    suspend fun getCurrentOrder(): List<Item>

    suspend fun loadItemList(): List<Any>
}
package com.example.coffeshop.data

import android.app.Application
import android.util.Log
import com.example.coffeshop.domain.entity.Client
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import com.example.coffeshop.domain.entity.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CoffeeShopRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val mapper: CoffeeShopMapper
) : CoffeeShopRepository {

    override suspend fun saveClient(
        client: Client, password: String,
        weakPasswordCallback: () -> Unit,
        userExistsCallback: () -> Unit,
        successCallback: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(client.email, password)
            .addOnCompleteListener {

                Log.d("RegTest", it.exception?.toString() ?: "Success")

                it.exception?.let { it1 ->
                    if (Regex(".*FirebaseAuthUserCollisionException.*")
                            .matches(it1.toString())
                    ) {
                        userExistsCallback()
                    }

                    if (Regex(".*FirebaseAuthWeakPasswordException.*")
                            .matches(it1.toString())
                    ) {
                        weakPasswordCallback()
                    }
                }

                it.addOnSuccessListener {
                    addClientToDb(client, successCallback)
                }
            }
    }

    private fun addClientToDb(client: Client, successCallback: () -> Unit) {

        val counterRef = database.collection(USERS_COLLECTION).document(USERS_COUNTER)

        database.runTransaction { transaction ->
            val snapshot = transaction.get(counterRef)
            var lastId = snapshot.getLong(USERS_COUNTER_VAL) ?: 0
            lastId++
            transaction.update(counterRef, USERS_COUNTER_VAL, lastId)

            val clientToDb = client.copy(id = lastId)

            database.collection(USERS_COLLECTION).document()
                .set(clientToDb)

        }.addOnSuccessListener {
            successCallback()
        }
    }

    override suspend fun saveToOrder(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun isLogged(): Long {
        val currentUser = auth.currentUser
        var userId = 0L
        when (currentUser) {
            null -> return 0L
            else -> {
                database.collection(USERS_COLLECTION)
                    .whereEqualTo(USER_EMAIL, currentUser.email)
                    .get()
                    .addOnSuccessListener {
                        Log.d("RegTest", "${it.documents[0]?.get(USER_ID)}")
                        if (it.documents.isEmpty()) throw RuntimeException(
                            "User with email: " +
                                    "${currentUser.email} not exists"
                        )
                        else {
                            userId = it.documents[0]?.get(USER_ID).toString().toLong()
                        }
                    }
                    .addOnFailureListener { throw RuntimeException("cant get user by email") }
                    .await()
                return userId
            }
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun login(email: String, password: String, successCallback: () -> Unit) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                it.addOnSuccessListener {
                    successCallback()
                }

                it.addOnFailureListener { exception ->
                    Log.d("LoginTest", exception.toString())
                }
            }

    }

    override suspend fun getListItem(): List<Any> {
        val list = mutableListOf<Any>()

        list.add(DRINKS_COLLECTION)
        database.collection(DRINKS_COLLECTION).get()
            .addOnSuccessListener {
                it.documents.forEach { item ->
                    list.add(
                        Item(
                            name = item.get(ITEM_NAME).toString(),
                            cost = item.get(ITEM_COST).toString().toDouble(),
                            category = DRINKS_COLLECTION,
                            image = item.get(ITEM_IMAGE).toString()
                        )
                    )
                }
            }.await()

        list.add(FOOD_COLLECTION)
        database.collection(FOOD_COLLECTION).get()
            .addOnSuccessListener {
                it.documents.forEach { item ->
                    list.add(
                        Item(
                            name = item.get(ITEM_NAME).toString(),
                            cost = item.get(ITEM_COST).toString().toDouble(),
                            category = FOOD_COLLECTION,
                            image = item.get(ITEM_IMAGE).toString()
                        )
                    )
                }

            }.await()

        list.add(HOME_COLLECTION)
        database.collection(HOME_COLLECTION).get()
            .addOnSuccessListener {
                it.documents.forEach { item ->
                    list.add(
                        Item(
                            name = item.get(ITEM_NAME).toString(),
                            cost = item.get(ITEM_COST).toString().toDouble(),
                            category = HOME_COLLECTION,
                            image = item.get(ITEM_IMAGE).toString()
                        )
                    )
                }

            }.await()

        list.add(MERCH_COLLECTION)
        database.collection(MERCH_COLLECTION).get()
            .addOnSuccessListener {
                it.documents.forEach { item ->
                    list.add(
                        Item(
                            name = item.get(ITEM_NAME).toString(),
                            cost = item.get(ITEM_COST).toString().toDouble(),
                            category = MERCH_COLLECTION,
                            image = item.get(ITEM_IMAGE).toString()
                        )
                    )
                }

            }.await()

        return list.toList()
    }

    companion object {

        private const val USER_ID = "id"
        private const val USERS_COUNTER = "counter"
        private const val USERS_COLLECTION = "clients"
        private const val USERS_COUNTER_VAL = "lastId"
        private const val USER_EMAIL = "email"
        private const val DRINKS_COLLECTION = "Drinks"
        private const val FOOD_COLLECTION = "Food"
        private const val HOME_COLLECTION = "At home coffee"
        private const val MERCH_COLLECTION = "Merchandize"
        private const val ITEM_NAME = "name"
        private const val ITEM_COST = "cost"
        private const val ITEM_IMAGE = "image"
    }
}
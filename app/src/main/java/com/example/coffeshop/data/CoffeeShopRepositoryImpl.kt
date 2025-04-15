package com.example.coffeshop.data

import android.util.Log
import com.example.coffeshop.domain.entity.Client
import com.example.coffeshop.domain.entity.Item
import com.example.coffeshop.domain.entity.Order
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeShopRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val mapper: CoffeeShopMapper,
    private val orderListDao: OrderListDao
) : CoffeeShopRepository {

    private lateinit var listItem: List<Any>

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

        database.runTransaction { transaction ->
            database.collection(USERS_COLLECTION).document(auth.currentUser!!.uid.toString())
                .set(client)

        }.addOnSuccessListener {
            successCallback()
        }
    }

    override suspend fun isLogged(): Boolean {
        Log.d("UserIdTest", auth.currentUser?.uid.toString())
        return auth.currentUser != null
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

    override fun getListItem(): List<Any> {
        return listItem
    }

    override fun getItem(itemId: Int): Item {
        return listItem[itemId] as Item
    }

    override suspend fun deleteFromOrder(itemId: Int) {
        orderListDao.deleteFromOrder(itemId)
    }

    override suspend fun cleanOrder() {
        orderListDao.cleanOrder()
    }

    override fun getCurrentOrder(): Flow<List<Item>> =
        orderListDao.getOrderList().map {
        mapper.listDbModelToListItem(it)
    }

    override suspend fun loadItemList(): List<Any> {
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

        listItem = list.toList()
        return list
    }

    override suspend fun placeOrder(
        listItem: List<Item>,
        onSuccessCallback: (Int) -> Unit
    ) {
        val order = Order(listItem)
        val docRef = database.collection(CLIENT_COLLECTION).document(auth.currentUser!!.uid)
        val list = getOrders()
        Log.d("SaveOrderTest", list.toString())
        list.add(order)
        Log.d("SaveOrderTest", list.toString())
        docRef.update(USER_ORDERS, list)
        onSuccessCallback(list.size - 1)
    }

    private suspend fun getOrders(): MutableList<Order> {
        val docRef = database.collection(CLIENT_COLLECTION).document(auth.currentUser!!.uid)
        val snapshot = docRef.get().await()
        val firebaseList = snapshot.get(USER_ORDERS) as MutableList<Map<String, Any>>
        return firebaseList.map { order ->
            val items = (order["listItem"] as List<Map<String, Any>>).map { item ->
                Item(
                    name = item["name"] as String,
                    cost = item["cost"] as Double,
                    category = item["category"] as String,
                    image = item["image"] as String,
                    size = item["size"] as String,
                    sugar = item["sugar"] as String,
                    syrup = item["syrup"] as String
                )
            }
            Order(listItem = items, status = order["status"] as String)
        }.toMutableList()
    }

    override suspend fun getOrderStatusUseCase(orderId: Int): Flow<String> = callbackFlow {

        val listener = database.collection(CLIENT_COLLECTION)
            .document(auth.currentUser!!.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                snapshot.let {
                    val orders = it?.get(USER_ORDERS) as List<Map<String, Any>>
                    val status = orders[orderId]["status"] as String
                    trySend(status)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun saveToOrder(item: Item) {
        orderListDao.addToOrder(mapper.itemToDbModel(item))
    }

    companion object {
        private const val USERS_COLLECTION = "clients"
        private const val USER_ORDERS = "orders"
        private const val DRINKS_COLLECTION = "Drinks"
        private const val FOOD_COLLECTION = "Food"
        private const val HOME_COLLECTION = "At home coffee"
        private const val MERCH_COLLECTION = "Merchandize"
        private const val CLIENT_COLLECTION = "clients"
        private const val ITEM_NAME = "name"
        private const val ITEM_COST = "cost"
        private const val ITEM_IMAGE = "image"
    }
}
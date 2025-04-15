package com.example.coffeshop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderListDao {

    @Query("SELECT * FROM order_items")
    fun getOrderList(): Flow<List<OrderItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToOrder(orderItemDbModel: OrderItemDbModel)

    @Query("DELETE FROM order_items WHERE id=:itemId")
    fun deleteFromOrder(itemId: Int)

    @Query("DELETE FROM order_items")
    fun cleanOrder()

    @Query("SELECT * FROM order_items WHERE id=:itemId")
    fun getOrderItem(itemId: Int): OrderItemDbModel
}
package com.example.coffeshop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderListDao {

    @Query("SELECT * FROM order_items")
    fun getOrderList(): List<OrderItemDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToOrder(orderItemDbModel: OrderItemDbModel)

    @Query("DELETE FROM order_items WHERE id=:itemId")
    fun deleteFromOrder(itemId: Int)

    @Query("SELECT * FROM order_items WHERE id=:itemId")
    fun getOrderItem(itemId: Int): OrderItemDbModel
}
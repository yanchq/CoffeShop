package com.example.coffeshop.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrderItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderListDao(): OrderListDao

    companion object {
        const val DB_NAME = "order_item.db"
    }
}
package com.example.coffeshop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CoffeeShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToorder(clientDbModel: ClientDbModel)

//    @Query("SELECT * FROM clients")
//    suspend fun isLogged():
}
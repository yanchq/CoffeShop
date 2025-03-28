package com.example.coffeshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("clients")
data class ClientDbModel(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String
) {
}
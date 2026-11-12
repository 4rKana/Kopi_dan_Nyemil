package com.android.kopidannyemil.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,
    val menuItemId: Int,
    val name: String,
    val price: Long,
    val quantity: Int
)

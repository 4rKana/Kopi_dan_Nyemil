package com.android.kopidannyemil.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,
    val timestamp: Long,
    val totalPrice: Long,
    val itemsSummary: String
)

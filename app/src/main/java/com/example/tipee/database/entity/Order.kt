package com.example.tipee.database.entity

import androidx.room.Entity
import java.util.*

@Entity(primaryKeys = ["productId"])
data class Order (
    val orderId: String = UUID.randomUUID().toString().substring(0, 8),
    val productId: String,
    val productName: String,
    var quantity: Int,
    val thumbnailUrl: String,
    val price: Int,
    val listPrice: Int
)
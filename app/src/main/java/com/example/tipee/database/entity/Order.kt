package com.example.tipee.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Order (
    @PrimaryKey val orderId: String = UUID.randomUUID().toString().substring(0, 8),
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "list_price") val listPrice: Int
)
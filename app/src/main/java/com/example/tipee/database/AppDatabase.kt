package com.example.tipee.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tipee.database.dao.OrderDao
import com.example.tipee.database.dao.ProductDao
import com.example.tipee.database.entity.Order
import com.example.tipee.model.ProductDetail

@Database(entities = [Order::class, ProductDetail::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDao() : OrderDao
    abstract fun productDao() : ProductDao
}
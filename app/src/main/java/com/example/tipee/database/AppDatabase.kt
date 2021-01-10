package com.example.tipee.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tipee.database.dao.OrderDao
import com.example.tipee.database.entity.Order

@Database(entities = arrayOf(Order::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDao() : OrderDao
}
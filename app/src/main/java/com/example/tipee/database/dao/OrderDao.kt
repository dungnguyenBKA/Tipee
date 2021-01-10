package com.example.tipee.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tipee.database.entity.Order
import io.reactivex.rxjava3.core.Completable

@Dao
interface OrderDao {
    @Query("select * from `order`")
    suspend fun getAll(): List<Order>

    @Insert
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun delete(order: Order)

}
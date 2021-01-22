package com.example.tipee.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.tipee.database.entity.Order

@Dao
interface OrderDao {
    @Query("select * from `order`")
    suspend fun getAll(): List<Order>

    @Insert(onConflict = REPLACE)
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("select * from `order` where productId like :productId")
    suspend fun findOrderByProductId(productId: String): List<Order>

    @Query("DELETE FROM `order`")
    suspend fun nukeTable()
}
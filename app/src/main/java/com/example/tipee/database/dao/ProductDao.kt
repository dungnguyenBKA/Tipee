package com.example.tipee.database.dao

import androidx.room.*
import com.example.tipee.model.ProductDetail

@Dao
interface ProductDao {
    @Query("select * from `productdetail`")
    suspend fun getAll(): List<ProductDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productDetail: ProductDetail)

    @Delete
    suspend fun delete(productDetail: ProductDetail)

    @Query("select * from `productdetail` where id like :productId")
    suspend fun findProductById(productId: String): List<ProductDetail>
}
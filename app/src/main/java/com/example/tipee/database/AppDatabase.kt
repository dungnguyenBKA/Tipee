package com.example.tipee.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tipee.database.dao.OrderDao
import com.example.tipee.database.dao.ProductDao
import com.example.tipee.database.entity.Order
import com.example.tipee.model.ProductDetail
import com.example.tipee.screen.productdetail.adapter.ShopDetail
import com.google.gson.Gson

@Database(entities = [Order::class, ProductDetail::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDao() : OrderDao
    abstract fun productDao() : ProductDao
}
class Converters {
    @TypeConverter
    fun toShopDetail(json: String): ShopDetail? {
        return Gson().fromJson(json, ShopDetail::class.java) ?: null
    }

    @TypeConverter
    fun fromShopDetail(novelDetail: ShopDetail): String {
        return Gson().toJson(novelDetail) ?: ""
    }

    @TypeConverter
    fun toListShopDetail(json: String):  List<ShopDetail> {
        return emptyList()
    }

    @TypeConverter
    fun fromListShopDetail(novelDetail: List<ShopDetail>): String {
        return Gson().toJson(novelDetail) ?: ""
    }
}
package com.example.tipee.model

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class ProductDetail(
    var id: String,
    var name: String,
    var url_path: String,
    var productset_group_name: String,
    var min_qty: Int,
    var max_qty: Int,
    var quantity: Int,
    var short_description: String,
    var description: String,
    var thumbnail_url: String,
    var price: Int,
    var list_price: Int,
    var isLike: Boolean = false
)
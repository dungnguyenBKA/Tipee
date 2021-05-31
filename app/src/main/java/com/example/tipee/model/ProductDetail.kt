package com.example.tipee.model

import androidx.room.Entity
import com.example.tipee.screen.productdetail.adapter.ShopDetail

@Entity(primaryKeys = ["id"])
data class ProductDetail(
    var id: String = "",
    var name: String= "",
    var url_path: String= "",
    var productset_group_name: String= "",
    var min_qty: Int = 0,
    var max_qty: Int= 0,
    var quantity: Int= 0,
    var short_description: String = "",
    var description: String= "",
    var thumbnail_url: String= "",
    var price: Int= 0,
    var list_price: Int = 0,
    var isLike: Boolean = false,
    var current_seller: ShopDetail = ShopDetail(),
    var other_sellers: List<ShopDetail> = mutableListOf()
)
package com.example.tipee.model.response

import com.example.tipee.model.ProductDetail

data class HomepageItem (
    var id_category: String,
    var name: String,
    var product_set: List<ProductDetail>
)
package com.example.tipee.model.response

import com.example.tipee.model.ProductDetail

data class TabItem(
    var items: List<ProductDetail>,
    var icon: String = ""
)
package com.example.tipee.utils.event

import com.example.tipee.database.entity.Order

data class AddCartEvent(
    var order: Order
)
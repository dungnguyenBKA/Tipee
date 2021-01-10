package com.example.tipee.utils

import java.text.NumberFormat

class MoneyUtils {
    companion object{
        fun toVND(value: Int): String{
            return "${NumberFormat.getInstance().format(value)} đ"
        }
    }
}
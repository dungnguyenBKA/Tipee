package com.example.tipee.utils

import java.text.NumberFormat

class MoneyUtils {
    companion object{
        fun toVND(value: Int): String{
            return "${NumberFormat.getInstance().format(value)} đ"
        }

        fun disCountUtils(price: Int, realPrice: Int): String{
            return if(price >= realPrice){
                ""
            } else {
                val discountPercent = (realPrice-price)*100/realPrice
                "-$discountPercent%"
            }
        }
    }
}
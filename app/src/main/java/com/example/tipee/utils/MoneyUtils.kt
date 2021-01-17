package com.example.tipee.utils

import android.graphics.Paint
import android.widget.TextView
import java.text.NumberFormat

class MoneyUtils {
    companion object{
        fun toVND(value: Int): String{
            return "${NumberFormat.getInstance().format(value)} đ"
        }

        fun setTextDiscount(value: Int, textView: TextView){
            textView.apply {
                text = toVND(value)
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
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
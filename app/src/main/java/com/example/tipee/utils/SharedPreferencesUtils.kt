package com.example.tipee.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.tipee.R

class SharedPreferencesUtils {
    companion object{
        const val TOKEN = "login_token"
        private lateinit var pre : SharedPreferences
        fun getInstance(context: Context): SharedPreferencesUtils{
            pre = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            return SharedPreferencesUtils()
        }
    }

    fun putString(fieldName: String, strValue: String){
        val editor = pre.edit()
        editor.putString(fieldName, strValue)
        editor.apply()
    }

    fun getString(fieldName: String): String{
        return pre.getString(fieldName, "") ?: ""
    }
}
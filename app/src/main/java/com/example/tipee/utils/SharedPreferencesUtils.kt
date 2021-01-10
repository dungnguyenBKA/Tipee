package com.example.tipee.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.tipee.R

class SharedPreferencesUtils private constructor(){
    companion object{
        var context: Context? = null
        private lateinit var sharedPreferencesUtils : SharedPreferencesUtils
        fun getInstance(): SharedPreferencesUtils{
            if(!this::sharedPreferencesUtils.isInitialized){
                sharedPreferencesUtils = SharedPreferencesUtils()
            }
            return sharedPreferencesUtils
        }
    }

    private var pre : SharedPreferences? = context?.getSharedPreferences(context?.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun putString(fieldName: String, strValue: String){
        val editor = pre?.edit()
        editor?.putString(fieldName, strValue)
        editor?.apply()
    }

    fun getString(fieldName: String): String{
        return pre?.getString(fieldName, "") ?: ""
    }

    fun putInt(fieldName: String, value: Int){
        val editor = pre?.edit()
        editor?.putInt(fieldName, value)
        editor?.apply()
    }
}
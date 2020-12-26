package com.example.tipee.utils

import android.content.Context

class LoginUtils {
    companion object{
        fun isLoginWithRequest(context: Context): Boolean{
            if(SharedPreferencesUtils.getInstance(context).getString(SharedPreferencesUtils.TOKEN) != ""){
                return true
            }
            return false
        }
    }
}
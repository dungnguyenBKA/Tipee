package com.example.tipee.utils

class LoginUtils {
    companion object{
        fun isLoginWithRequest(): Boolean{
            if(SharedPreferencesUtils.getInstance().getString(Constant.LOGIN_TOKEN) != ""){
                return true
            }
            return false
        }
    }
}
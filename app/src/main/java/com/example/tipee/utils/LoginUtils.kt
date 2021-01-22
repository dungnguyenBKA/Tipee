package com.example.tipee.utils

import com.example.tipee.model.UserDetail
import com.example.tipee.utils.event.LoginEvent
import org.greenrobot.eventbus.EventBus

class LoginUtils {
    companion object{
        fun isLogin(): Boolean{
            if(SharedPreferencesUtils.getInstance().getString(Constant.USER_ID) != ""){
                return true
            }
            return false
        }

        fun login(userDetail: UserDetail){
            SharedPreferencesUtils.getInstance().putString(Constant.USER_ID, userDetail.userId)
            SharedPreferencesUtils.getInstance().putString(Constant.USER_NAME, userDetail.username)
            SharedPreferencesUtils.getInstance().putString(Constant.USER_REAL_NAME, userDetail.userRealName)
            SharedPreferencesUtils.getInstance().putString(Constant.USER_URL_AVATAR, userDetail.userAvatarUrl)
            SharedPreferencesUtils.getInstance().putInt(Constant.USER_BALANCE, 10000000)
            EventBus.getDefault().post(LoginEvent())
        }

        fun logout(){
            SharedPreferencesUtils.getInstance().putString(Constant.USER_ID, "")
            SharedPreferencesUtils.getInstance().putString(Constant.USER_NAME, "")
            SharedPreferencesUtils.getInstance().putString(Constant.USER_REAL_NAME, "")
            SharedPreferencesUtils.getInstance().putString(Constant.USER_URL_AVATAR, "")
            SharedPreferencesUtils.getInstance().putInt(Constant.USER_BALANCE, 0)
        }

        fun getAvatar(): String{
            return SharedPreferencesUtils.getInstance().getString(Constant.USER_URL_AVATAR)
        }

        fun getUserName(): String{
            return SharedPreferencesUtils.getInstance().getString(Constant.USER_NAME)
        }

        fun getUserRealName(): String{
            return SharedPreferencesUtils.getInstance().getString(Constant.USER_REAL_NAME)
        }

        fun getUserId(): String{
            return SharedPreferencesUtils.getInstance().getString(Constant.USER_ID)
        }

        fun getUserBalance(): Int{
            return SharedPreferencesUtils.getInstance().getInt(Constant.USER_BALANCE)
        }

        fun setUserBalance(value: Int){
            SharedPreferencesUtils.getInstance().putInt(Constant.USER_BALANCE, value)
        }

        fun getUserDetail(): UserDetail{
            return UserDetail(
                getUserId(),
                getUserName(),
                "",
                getUserRealName(),
                100000,
                getAvatar()
            )
        }
    }
}
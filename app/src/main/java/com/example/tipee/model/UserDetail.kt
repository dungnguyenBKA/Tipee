package com.example.tipee.model

import com.google.gson.annotations.SerializedName

data class UserDetail (
    @SerializedName("id")
    var userId: String = "",
    @SerializedName("name")
    var username: String= "",
    var password: String= "",
    @SerializedName("full_name")
    var userRealName: String= "",
    var balance: Int = 0,
    @SerializedName("avatar_url")
    var userAvatarUrl: String= ""
)
package com.example.tipee.model

data class UserDetail (
    var userId: String,
    var username: String,
    var password: String,
    var userRealName: String,
    var balance: Int,
    var userAvatarUrl: String
)
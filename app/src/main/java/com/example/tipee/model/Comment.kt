package com.example.tipee.model

data class Comment(
    var userId: String,
    var userName: String,
    var userRealName: String,
    var comment: String,
    var rating: Float,
    var user_avatar: String
)
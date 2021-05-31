package com.example.tipee.model

data class Review (
    var title: String = "",
    var content: String = "",
    var rating: Float = 0f,
    var created_by: UserDetail = UserDetail(),
    var created_at: Long = 0
)
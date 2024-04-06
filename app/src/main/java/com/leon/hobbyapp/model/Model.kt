package com.leon.studentapp.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)

data class News(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val createdBy: String,
    val date: String,
    val content: List<String>
)


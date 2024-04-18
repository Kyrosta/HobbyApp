package com.leon.hobbyapp.model

data class User(
    val id: Int?,
    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
)

data class Hobby(
    val id: Int?,
    val title: String?,
    val createdBy: String?,
    val imageUrl: String?,
    val description: String?,
    val content: String?,
)


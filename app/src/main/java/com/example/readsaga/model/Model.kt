package com.example.readsaga.model

data class User(
    val id: String?,
    val email: String?,
    val username: String?,
    val first_name: String?,
    val last_name: String?,
    val password: String?,
    val url_photo: String?
)

data class Books(
    val id: String?,
    val title: String?,
    val image: String?,
    val description: String?,
    val comment: String?,
    val author: String?,
    val sender: String?
)
package com.example.readsaga.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("first_name")
    val first_name: String?,
    @SerializedName("last_name")
    val last_name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("url_photo")
    val url_photo: String?
)

data class Books(
    val id: String?,
    val title: String?,
    val image: String?,
    val description: String?,
    var comment: String?,
    val author: String?,
    val sender: String?
)
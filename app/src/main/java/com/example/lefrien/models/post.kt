package com.example.lefrien.models


data class Post (
    val text: String = "",
    val createdBy: user = user(),
    val createdAt: Long = 0L,
    val likedBy: ArrayList<String> = ArrayList())
package com.example.lefrien.models

data class user(
    var uid: String? = null,
    var name: String ?= null,
    var age: String ?= null,
    var des: String ?= null,
    var email: String ?= null,
    var list: ArrayList<String> = ArrayList()
)


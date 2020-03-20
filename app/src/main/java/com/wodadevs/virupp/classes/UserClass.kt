package com.wodadevs.virupp.classes

data class UserClass (
var last_shop: Long? = null,
var last_wash: Long? = null,
var streak: Int? = null,
var washes: Int? = null,
var shops: List<String>?= null,
var id: String ?= null
)
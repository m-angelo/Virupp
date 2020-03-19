package com.wodadevs.virupp.classes

data class UserClass (
var last_shop: Long? = null,
var last_wash: Long? = null,
var streak: Int? = null,
var washes: Int? = null,
val shops: List<String>?= null
)
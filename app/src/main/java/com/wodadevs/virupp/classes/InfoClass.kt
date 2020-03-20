package com.wodadevs.virupp.classes

data class InfoClass (
    val title: String ="",
    val short: String ="",
    val long: String ="",
    val article3: String ="",
    val data1:Int = 0,
    val data2:Int = 0,
    val data3:Int = 0 ,
    val nestedData:List<InfoClass>?=null)
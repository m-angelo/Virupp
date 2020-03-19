package com.wodadevs.virupp.api

object Common {

    private val GOOGLE_API_URL="https://maps.googleapis.com/"

    val googleApiService:ShopsApi
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(ShopsApi::class.java)
}
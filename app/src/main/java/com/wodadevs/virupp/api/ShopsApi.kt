package com.wodadevs.virupp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ShopsApi {
    @GET
    fun getNearbyPlaces(@Url url:String): Call<Places>
}
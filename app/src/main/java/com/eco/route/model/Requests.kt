package com.eco.route.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Requests {
    private const val MAIN_URL =""
    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val server : ServerApi by lazy {
        retrofitInstance.create(ServerApi::class.java)
    }
}
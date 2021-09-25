package com.eco.route.model

import com.eco.route.data.Zone
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerApi  {
    @GET("alldata/{page}")
    suspend fun getZones(@Path("page") page : Int) : Response<List<Zone>>
}
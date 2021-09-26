package com.eco.route.model

import com.eco.route.data.DataStreet
import com.eco.route.data.Zone
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerApi  {
    @GET("alldata/{page}")
    fun getZones(@Path("page") page : Int) : Call<List<Zone>>
    @GET("street/{name}")
    fun getStreet(@Path("name") name : String) : Call<DataStreet>
}
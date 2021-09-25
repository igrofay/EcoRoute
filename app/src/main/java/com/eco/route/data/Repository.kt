package com.eco.route.data

import com.eco.route.model.Requests

class Repository {
    suspend fun getZones(page : Int) = Requests.server.getZones( page )
    suspend fun getStreet(name : String) = Requests.server.getStreet( name )
}
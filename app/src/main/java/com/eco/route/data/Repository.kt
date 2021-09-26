package com.eco.route.data

import com.eco.route.model.Requests

class Repository {
    fun getZones(page : Int) = Requests.server.getZones( page )
    fun getStreet(name : String) = Requests.server.getStreet( name )
}
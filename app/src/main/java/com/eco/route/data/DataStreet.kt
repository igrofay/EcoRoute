package com.eco.route.data

import com.google.gson.annotations.SerializedName

data class DataStreet(
    @SerializedName("street")
    val name: String,
    val values: Values
)

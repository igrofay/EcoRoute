package com.eco.route.data

data class Zone(
    val street : String,
    val contaminationLevel : Double,
    val sensors : List< List<Double> >
)

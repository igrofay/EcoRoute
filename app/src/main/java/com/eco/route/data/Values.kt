package com.eco.route.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Values (
    val  temperature:Double,
    val humidity: Double,
    val co2:Double,
    val los: Double,
    val dustPm1:Double,
    val dustPm25 :Double,
    val dustPm10 :Double,
    val pressure: Double,
    val aqi:Double,
    val formaldehyde:Double
        ) : Parcelable


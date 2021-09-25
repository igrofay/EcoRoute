package com.eco.route.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataStreet(
    @SerializedName("street")
    val name: String,
    val values: Values
) : Parcelable

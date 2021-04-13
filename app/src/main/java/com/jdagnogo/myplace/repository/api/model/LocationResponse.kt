package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("address") val address: String = "",
    @field:SerializedName("lat") val lat: Long = 0L,
    @field:SerializedName("lng") val lng: Long = 0L,
    @field:SerializedName("postalCode") val postalCode:  String = ""
)
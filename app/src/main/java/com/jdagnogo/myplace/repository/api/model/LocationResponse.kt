package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("address") val address: String = "",
    @field:SerializedName("lat") val lat: Float = 0F,
    @field:SerializedName("lng") val lng: Float = 0F
)
package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class VenueResponse(
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("location") val location: LocationResponse = LocationResponse()
)
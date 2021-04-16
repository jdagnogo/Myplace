package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class VenueDetailsApiResponse(
    @SerializedName("response") val data : VenueDetailsContentResponse = VenueDetailsContentResponse()
)
package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class VenueDetailsContentResponse(
    @SerializedName("venue") val venueDetails: VenueDetailsResponse = VenueDetailsResponse()
)
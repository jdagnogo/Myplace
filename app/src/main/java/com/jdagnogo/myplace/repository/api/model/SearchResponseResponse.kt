package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class SearchResponseResponse(
        @SerializedName("venues") val venues : List<VenueResponse> = emptyList()
)
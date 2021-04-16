package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class SearchContentResponse(
        @SerializedName("venues") val venues : List<VenueResponse> = emptyList()
)
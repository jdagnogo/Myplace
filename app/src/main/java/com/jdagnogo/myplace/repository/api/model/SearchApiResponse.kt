package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class SearchApiResponse(
    @SerializedName("response") val data : SearchContentResponse = SearchContentResponse()
)
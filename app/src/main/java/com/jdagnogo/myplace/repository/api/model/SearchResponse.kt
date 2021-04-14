package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("response") val data : SearchResponseResponse = SearchResponseResponse()
)
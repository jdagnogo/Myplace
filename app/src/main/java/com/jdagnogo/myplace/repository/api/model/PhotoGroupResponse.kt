package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class PhotoGroupResponse(
        @field:SerializedName("items") val items: List<PhotoItemsResponse> = emptyList()
)
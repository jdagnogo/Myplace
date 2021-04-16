package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
        @field:SerializedName("groups") val groups: List<PhotoGroupResponse> = emptyList()
)
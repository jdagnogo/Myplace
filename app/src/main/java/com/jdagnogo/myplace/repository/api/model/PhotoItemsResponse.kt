package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class PhotoItemsResponse(
        @field:SerializedName("prefix") val prefix: String = "",
        @field:SerializedName("suffix") val suffix: String = ""
)
package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class ContactInfoResponse(
    @field:SerializedName("phone") val phone: String = "",
    @field:SerializedName("formattedPhone") val formattedPhone: String = "",
    @field:SerializedName("facebookName") val facebookName: String = "",
    @field:SerializedName("facebookUsername") val facebookUsername: String = "",
    @field:SerializedName("facebook") val facebook: String = "",
    @field:SerializedName("twitter") val twitter: String = ""
)
package com.jdagnogo.myplace.repository.api.model

import com.google.gson.annotations.SerializedName

data class VenueDetailsResponse(
        @field:SerializedName("id") val id: String = "",
        @field:SerializedName("name") val name: String = "",
        @field:SerializedName("description") val description: String = "",
        @field:SerializedName("rating") val rating: Float = 0f,
        @field:SerializedName("contact") val contact: ContactInfoResponse = ContactInfoResponse(),
        @field:SerializedName("photos") val photos: PhotoResponse = PhotoResponse(),
        @field:SerializedName("location") val location: LocationResponse = LocationResponse()
)
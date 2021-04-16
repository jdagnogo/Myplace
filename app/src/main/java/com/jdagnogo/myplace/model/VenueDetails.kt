package com.jdagnogo.myplace.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "venue_details")
data class VenueDetails(
    @PrimaryKey @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("phone") val phone: String = "",
    @field:SerializedName("facebook") val facebook: String = "",
    @field:SerializedName("twitter") val twitter: String = "",
    @field:SerializedName("location") val location: String = "",
    @field:SerializedName("photo") val photo: String = "",
    @field:SerializedName("rating") val rating: Float = 0f,
    @field:SerializedName("description") val description: String = ""
)
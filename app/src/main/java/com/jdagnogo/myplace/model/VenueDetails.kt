package com.jdagnogo.myplace.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "venue_details")
data class VenueDetails(
    @PrimaryKey @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("description") val description: String = ""
)
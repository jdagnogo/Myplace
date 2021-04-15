package com.jdagnogo.myplace.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "venue")
data class Venue(
    @PrimaryKey @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("query") val query: String = "",
    @field:SerializedName("address") val address: String = ""
)
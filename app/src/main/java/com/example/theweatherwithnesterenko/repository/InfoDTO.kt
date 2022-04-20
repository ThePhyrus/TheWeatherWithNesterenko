package com.example.theweatherwithnesterenko.repository


import com.google.gson.annotations.SerializedName

data class InfoDTO(
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("lon")
    val lon: Int,
    @SerializedName("url")
    val url: String
)
package com.faire.weather.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchWeatherResponse(
    @Json(name = "woeid")
    val id: Int = 0
)

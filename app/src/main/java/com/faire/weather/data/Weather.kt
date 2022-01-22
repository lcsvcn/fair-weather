package com.faire.weather.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class WeatherList(
    @Json(name = "consolidated_weather")
    val weatherList: List<Weather> = emptyList()
)


@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(

    @Json(name = "id")
    val id: Long = 0,

    @Json(name = "weather_state_name")
    val state: String? = null,

    @Json(name = "wind_direction_compass")
    val windDirection: String? = null,

    @Json(name = "applicable_date")
    val date: String? = null,

    @Json(name = "min_temp")
    val lowTemperature: Double? = null,

    @Json(name = "max_temp")
    val highTemperature: Double? = null,

    @Json(name = "the_temp")
    val currentTemperature: Double? = null,

    @Json(name = "humidity")
    val humidity: String? = null,

) : Parcelable
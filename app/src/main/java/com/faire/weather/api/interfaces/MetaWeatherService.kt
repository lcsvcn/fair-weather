package com.faire.weather.api.interfaces

import com.faire.weather.api.data.SearchWeatherResponse
import com.faire.weather.api.data.WeatherListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaWeatherService {

    @GET("/api/location/{woeid}")
    suspend fun getWeatherList(@Path("woeid") woeid: Int): Response<WeatherListResponse>

    @GET("/api/location/search")
    suspend fun getWeatherId(@Query("query") search: String): Response<List<SearchWeatherResponse>>

}
package com.faire.weather.model

import com.faire.weather.data.SearchWeatherResponse
import com.faire.weather.data.WeatherListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MetaWeatherService {

    @GET
    fun getWeatherList(@Url url: String): Call<WeatherListResponse>

    @GET
    fun getWeatherId(@Url url: String, @Query("query") search : String?): Call<SearchWeatherResponse>

}
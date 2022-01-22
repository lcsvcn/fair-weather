package com.faire.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faire.weather.api.data.SearchWeatherResponse
import com.faire.weather.api.data.Weather
import com.faire.weather.api.data.WeatherListResponse
import com.faire.weather.arch.ErrorState
import com.faire.weather.arch.LoadingState
import com.faire.weather.api.interfaces.MetaWeatherService
import javax.inject.Inject
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.HttpException
import retrofit2.Response


class WeatherListViewModel @Inject constructor() : ViewModel() {

    val loadingLiveData = MutableLiveData<LoadingState>()
    val errorLiveData = MutableLiveData<ErrorState>()
    val emptyLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<List<Weather>>()
    val scrollLiveData = MutableLiveData<Boolean>()

    private lateinit var api: MetaWeatherService

    companion object {
        const val BASE_URL = "https://www.metaweather.com/"
    }

    fun showEmptyState() = emptyLiveData.postValue(true)

    fun setupApi() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        api = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(MetaWeatherService::class.java)
    }

    fun hideLoading() = loadingLiveData.postValue(LoadingState.Hide)

    fun showLoading() = loadingLiveData.postValue(LoadingState.Show)

    fun onQueryTextSubmit(query: String) = runBlocking {
        doSearchLocation(query)
    }

    fun onFabScrollTopTopClick() = scrollLiveData.postValue(true)

    private suspend fun doSearchLocation(search: String) {
        val response = api.getWeatherId(search)
        try {
            if (response.isSuccessful) {
                response.body()?.let { doListWeather(it.first().id) }
            } else {
                errorLiveData.postValue(
                    ErrorState(
                        "Our servers are lazy...",
                        "Status Code: ${response.code()}. Try again in some minutes"
                    )
                )
            }
        } catch (e: HttpException) {
            errorLiveData.postValue(
                ErrorState(
                    "Ops, we ran into an uncommon error",
                    "Status Code: ${response.code()}. We will be looking into this"
                )
            )
        } catch (e: Throwable) {
            errorLiveData.postValue(
                ErrorState(
                    "We couldn't find that place",
                    "Type another place and try again..."
                )
            )
        }
    }

    private suspend fun doListWeather(woeid: Int) {
        val response = api.getWeatherList(woeid)

        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.weatherList.isEmpty()) {
                        emptyLiveData.postValue(true)
                    } else {
                        emptyLiveData.postValue(false)
                    }
                    listLiveData.postValue(it.weatherList)
                }
            } else {
                errorLiveData.postValue(
                    ErrorState(
                        "Our servers are lazy...",
                        "Status Code: ${response.code()}. Try again in some minutes"
                    )
                )
            }
        } catch (e: HttpException) {
            errorLiveData.postValue(
                ErrorState(
                    "Ops, we ran into an uncommon error",
                    "Status Code: ${response.code()}. We will be looking into this"
                )
            )
        } catch (e: Throwable) {
            errorLiveData.postValue(
                ErrorState(
                    "Ouch! No results found",
                    "Please try with another local"
                )
            )
        }
    }
}
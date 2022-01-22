package com.faire.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faire.weather.arch.ErrorState
import com.faire.weather.arch.LoadingState
import com.faire.weather.arch.ViewState
import com.faire.weather.data.SearchWeatherResponse
import com.faire.weather.model.MetaWeatherService
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherListViewModel @Inject constructor() : ViewModel() {

    val loadingLiveData = MutableLiveData<LoadingState>()
    val errorLiveData = MutableLiveData<ErrorState>()
    val emptyLiveData = MutableLiveData<Boolean>()
    val scrollLiveData = MutableLiveData<ScrollState>()

    private lateinit var retrofit: Retrofit

    private lateinit var service: MetaWeatherService

    companion object {
        const val BASE_URL = "https://www.metaweather.com/api/"
    }

    fun setupRetrofit() {
        retrofit = Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        service = retrofit.create(MetaWeatherService::class.java)
    }


    private fun hideLoading() = loadingLiveData.postValue(LoadingState.Hide)

    private fun showLoading() = loadingLiveData.postValue(LoadingState.Show)


    fun onQueryTextSubmit(query: String) = runBlocking {
        launch {
            showLoading()

            val call: Call<SearchWeatherResponse> = service.getWeatherId("location/search/", query)
            call.awaitResponse()

            hideLoading()
        }
    }

    fun onScrollToTopClick() {
        scrollLiveData.postValue(ScrollState.Top)
    }
}

sealed class ScrollState : ViewState {
    object Top : ScrollState()
}
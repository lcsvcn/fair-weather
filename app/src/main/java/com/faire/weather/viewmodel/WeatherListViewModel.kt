package com.faire.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faire.weather.arch.ErrorState
import com.faire.weather.arch.LoadingState
import com.faire.weather.data.Weather
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(): ViewModel() {

    val loadingLiveData = MutableLiveData<LoadingState>()
    val errorLiveData = MutableLiveData<ErrorState>()
    val emptyListLiveData = MutableLiveData<Boolean>()

    private val weatherList = mutableListOf<Weather>()

    fun hideLoading() = loadingLiveData.postValue(LoadingState.Hide)

    fun showLoading() = loadingLiveData.postValue(LoadingState.Show)
}


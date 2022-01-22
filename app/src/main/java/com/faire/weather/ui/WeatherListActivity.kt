package com.faire.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.faire.weather.R.layout
import com.faire.weather.adapter.WeatherListAdapter
import com.faire.weather.arch.LoadingState
import com.faire.weather.arch.gone
import com.faire.weather.arch.setValue
import com.faire.weather.arch.visible
import com.faire.weather.databinding.ActivityWeatherListBinding
import com.faire.weather.viewmodel.WeatherListViewModel
import androidx.activity.viewModels

class WeatherListActivity : AppCompatActivity() {

    private val viewModel: WeatherListViewModel by viewModels()

    private val binding by lazy { ActivityWeatherListBinding.inflate(layoutInflater) }

    private var adapter by lazy { WeatherListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_weather_list)
        setupViews()
        handleLoadingState()
        handleErrorState()
        handleEmptyListState()
    }

    private fun setupViews() {
        with(binding) {
            rcvWeatherList.adapter = this@WeatherListActivity.adapter
        }
    }

    private fun handleLoadingState() {
        viewModel.loadingLiveData.observe(this, {
            with(binding) {
                if (it is LoadingState.Show) {
                    pgrLoading.visible()
                    txtLoading.visible()
                    it.loadingMessage?.let {
                        txtLoading.text = it
                    }
                } else {
                    pgrLoading.gone()
                    txtLoading.gone()
                }
            }
        })
    }

    private fun handleErrorState() {
        viewModel.errorLiveData.observe(this, {
            with(binding) {
                it.message
            }
        })
    }

    private fun handleEmptyListState() {
        viewModel.errorLiveData.observe(this, {
            with(binding) {
                it.message
            }
        })
    }


}
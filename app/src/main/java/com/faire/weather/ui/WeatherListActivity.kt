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
import com.faire.weather.R
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import androidx.appcompat.widget.SearchView

class WeatherListActivity : AppCompatActivity() {

    private val viewModel: WeatherListViewModel by viewModels()

    private val binding by lazy { ActivityWeatherListBinding.inflate(layoutInflater) }

    private var adapter by lazy { WeatherListAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_weather_list)

        viewModel.setupRetrofit()
        setupViews()

        handleLoadingState()
        handleErrorState()
        handleEmptyListState()
        handleScrollTopState()
    }

    private fun setupViews() {
        with(binding) {
            rcvWeatherList.adapter = this@WeatherListActivity.adapter

            btnScrollToTop.setOnClickListener { viewModel.onScrollToTopClick() }
            btnScrollToTop.setOnClickListener { viewModel.onScrollToTopClick() }
            ctnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.onQueryTextSubmit(query)
                    return false
                }

                override fun onQueryTextChange(newText: String) = false
            })
        }
    }

    private fun handleLoadingState() {
        viewModel.loadingLiveData.observe(this, {
            with(binding) {
                if (it is LoadingState.Show) {
                    pgrLoadingIndicator.visible()
                } else {
                    pgrLoadingIndicator.gone()
                }
            }
        })
    }

    private fun handleErrorState() {
        viewModel.errorLiveData.observe(this, {
            with(binding) {
                if (it.title.isNotBlank() && it.message.isNotBlank()) {
                    ctnAlertMessage.visible()
                    txtTitle.text = it.title
                    txtMessage.text = it.message
                } else {
                    ctnAlertMessage.gone()
                }
            }
        })
    }

    private fun handleEmptyListState() {
        viewModel.emptyLiveData.observe(this, { isEmpty ->
            with(binding) {
                if (isEmpty) {
                    ctnAlertMessage.visible()
                    txtTitle.text = getString(R.string.empty_title);
                    txtTitle.text = getString(R.string.empty_message);
                } else {
                    ctnAlertMessage.gone()
                }
            }
        })
    }

    private fun handleScrollTopState() {
        viewModel.scrollLiveData.observe(this, {
            with(binding) {
                ctnAppBar.setExpanded(true)
                rcvWeatherList.apply {
                    stopScroll()
                    layoutManager?.scrollToPosition(0)
                }
            }
        })
    }
}
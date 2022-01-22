package com.faire.weather.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faire.weather.R
import com.faire.weather.arch.BaseViewHolder
import com.faire.weather.data.Weather
import com.faire.weather.adapter.WeatherListAdapter.WeatherViewHolder

class WeatherListAdapter: RecyclerView.Adapter<WeatherViewHolder>() {

    private val weatherList = mutableListOf<Weather>()

    fun setList(list: List<Weather>) {
        weatherList.clear()
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherViewHolder(parent)

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount() = weatherList.size

    inner class WeatherViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder(parent, R.layout.item_weather_card) {

//        private val txtTitle: TextView by bind(R.id.txt_title)

        fun bind(weather: Weather) {

        }
    }
}
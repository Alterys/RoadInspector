package com.example.roadinspector.presentation.screens.map.model

import com.example.roadinspector.data.remote.response.weather.Current
import com.example.roadinspector.data.remote.response.weather.ForecastDay
import com.example.roadinspector.data.remote.response.weather.Weather

data class WeatherItem(
    val forecastDay: List<ForecastDay>,
    val current: Current
    )

fun Weather.toWeatherItem(): WeatherItem {
    return WeatherItem(
       this.forecast.forecastday,
        this.current,
    )
}
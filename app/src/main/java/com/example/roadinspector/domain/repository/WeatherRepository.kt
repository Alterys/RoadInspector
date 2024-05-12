package com.example.roadinspector.domain.repository

import com.example.roadinspector.data.remote.response.weather.Weather

interface WeatherRepository {
    suspend fun getWeather(q: List<String>): List<Weather>

}
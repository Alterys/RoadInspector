package com.example.roadinspector.data.repository

import androidx.compose.ui.text.intl.Locale
import com.example.roadinspector.common.Constants.DAYS
import com.example.roadinspector.common.secret.Secret.API_KEY_WEATHER
import com.example.roadinspector.data.remote.response.weather.Weather
import com.example.roadinspector.domain.repository.WeatherRepository

object WeatherRepositoryImpl : WeatherRepository {
    private val api = ApiModule.weatherApi

    override suspend fun getWeather(q: List<String>): List<Weather> {
        val locale = Locale.current.language
        return q.map {
            api.getWeather(API_KEY_WEATHER, it, DAYS, locale)
        }
    }
}
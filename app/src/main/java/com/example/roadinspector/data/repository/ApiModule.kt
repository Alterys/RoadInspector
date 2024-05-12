package com.example.roadinspector.data.repository

import com.example.roadinspector.common.Constants
import com.example.roadinspector.data.remote.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiModule {
    val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_WEATHER_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

}
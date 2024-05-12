package com.example.roadinspector.data.remote

import com.example.roadinspector.data.remote.response.weather.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: Int,
        @Query("lang") lang: String
    ): Weather

}
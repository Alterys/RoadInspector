package com.example.roadinspector.data.remote.response.weather

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>
)

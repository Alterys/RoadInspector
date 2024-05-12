package com.example.roadinspector.data.remote.response.weather

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current") val current: Current,
    @SerializedName("forecast") val forecast: Forecast,
)

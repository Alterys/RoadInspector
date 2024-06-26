package com.example.roadinspector.data.remote.response.weather

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: Day
)
package com.example.roadinspector.data.remote.response.weather

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("condition") val condition: ForecastCondition,
    @SerializedName("maxwind_kph") val maxwindKph: Double,
    @SerializedName("totalprecip_mm") val totalPrecipMm: Double,
    @SerializedName("totalsnow_cm") val totalSnowCm: Double,
    @SerializedName("daily_will_it_rain") val willItRain: Int,
    @SerializedName("daily_will_it_snow") val willItSnow: Int,
)

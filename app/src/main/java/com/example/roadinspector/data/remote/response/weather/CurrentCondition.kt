package com.example.roadinspector.data.remote.response.weather

import com.google.gson.annotations.SerializedName

data class CurrentCondition(
    @SerializedName("text") val textCurrentCondition: String
)

package com.example.roadinspector.data.remote.response.transport

import com.google.gson.annotations.SerializedName

data class TransportResponse(
    @SerializedName("message") val message: String,
)
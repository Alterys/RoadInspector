package com.example.roadinspector.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("jwtToken") val token: String?,
)

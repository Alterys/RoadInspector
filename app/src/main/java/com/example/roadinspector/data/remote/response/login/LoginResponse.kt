package com.example.roadinspector.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val login: String,
    val password: String
)

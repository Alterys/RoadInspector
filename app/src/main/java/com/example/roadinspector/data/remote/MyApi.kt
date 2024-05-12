package com.example.roadinspector.data.remote

import com.example.roadinspector.data.remote.request.LoginRequest
import com.example.roadinspector.data.remote.response.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {
    @POST("/api/Account/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse


}
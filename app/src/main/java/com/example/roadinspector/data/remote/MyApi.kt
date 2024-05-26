package com.example.roadinspector.data.remote

import com.example.roadinspector.data.remote.request.LoginRequest
import com.example.roadinspector.data.remote.request.TransportRequest
import com.example.roadinspector.data.remote.response.login.LoginResponse
import com.example.roadinspector.data.remote.response.transport.TransportResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {

    @POST("api/Account/Login/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
    @POST("api/Account/RequestTransport")
    suspend fun requestTransport(
        @Body transportRequest: TransportRequest
    ): TransportResponse
}
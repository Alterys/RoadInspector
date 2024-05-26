package com.example.roadinspector.data.repository

import com.example.roadinspector.data.remote.request.LoginRequest
import com.example.roadinspector.data.remote.request.TransportRequest
import com.example.roadinspector.domain.model.ResultRequestTransport
import com.example.roadinspector.domain.repository.RequestTransportRepository

object RequestTransportRepositoryImpl : RequestTransportRepository {
    private val api = ApiModule.myApi
    override suspend fun requestTransport(
        email: String,
        comment: String,
        latitude: Double,
        longitude: Double
    ): ResultRequestTransport {
        val result = api.requestTransport(
            TransportRequest(
                email,
                latitude,
                longitude,
                comment
            )
        )
        return ResultRequestTransport(
            message = result.message
        )
    }

}
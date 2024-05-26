package com.example.roadinspector.domain.repository

import com.example.roadinspector.domain.model.ResultRequestTransport

interface RequestTransportRepository {

    suspend fun requestTransport(
        email: String,
        comment: String,
        latitude: Double,
        longitude: Double
    ): ResultRequestTransport
}
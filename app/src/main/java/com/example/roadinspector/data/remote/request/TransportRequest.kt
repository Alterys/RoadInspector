package com.example.roadinspector.data.remote.request

data class TransportRequest(
    val email: String,
    val coordinate_x: Double,
    val coordinate_y: Double,
    val comment: String

)

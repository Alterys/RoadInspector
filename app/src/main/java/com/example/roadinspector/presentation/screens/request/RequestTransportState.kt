package com.example.roadinspector.presentation.screens.request

data class RequestTransportState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)

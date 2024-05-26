package com.example.roadinspector.presentation.screens.map

import com.example.roadinspector.domain.model.WeatherItem
import com.yandex.mapkit.geometry.Point

data class MapState(
    val isLoading: Boolean = false,
    val sectors: Map<Int, List<Point>> = mapOf(),
    var onDialog: Boolean = false,
    val weatherList: List<WeatherItem>? = emptyList(),
    val coordinatesCenterSector: String? = null,
    val indexSnow: List<Int> = emptyList(),
    val indexRain: List<Int> = emptyList(),
    val indexDialog: Int? = null
)

package com.example.roadinspector.presentation.screens.map

import android.app.Application
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.universitywork.R
import com.example.roadinspector.common.Resource
import com.example.roadinspector.data.remote.response.weather.Weather
import com.example.roadinspector.domain.usecase.GetWeatherUseCase
import com.example.roadinspector.presentation.screens.map.model.toWeatherItem
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.Scanner

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(MapState())
    val screenState: StateFlow<MapState> = _screenState


    init {
        _screenState.update {
            it.copy(sectors = splitSegment(getPointList()))
        }
    }

    private fun getRoadCoordinates(): InputStream {
        val context = getApplication<Application>().applicationContext
        return context.resources.openRawResource(R.raw.don)

    }

    private fun getPointList(): List<Point> {
        val road = getRoadCoordinates()
        val scanner = Scanner(road, StandardCharsets.UTF_8.name())
        scanner.next()
        val pointList = mutableListOf<Point>()
        val stringList = mutableListOf<String>()
        while (scanner.hasNext()) {
            val line = scanner.next().split(',')
            val y = line[0].toDouble()
            val x = line[1].toDouble()
            stringList.add("$x,$y")
            pointList.add(Point(x, y))
        }
        return pointList
    }


    private fun splitSegment(pointList: List<Point>): Map<Int, List<Point>> {
        val segments = 10
        val segmentSize = pointList.size / segments
        val remainder = pointList.size % segments
        val segmentMap = mutableMapOf<Int, MutableList<Point>>()

        var segmentIndex = 0
        var currentSegmentSize = 0
        var additionalPointCount = 0

        for (i in 0 until segments) {
            segmentMap[i] = mutableListOf()
        }

        pointList.forEachIndexed { index, point ->
            segmentMap[segmentIndex]?.add(point)
            currentSegmentSize++

            if (currentSegmentSize == segmentSize + (if (additionalPointCount < remainder) 1 else 0)) {
                if (segmentIndex < segments - 1) {
                    segmentMap[segmentIndex + 1]?.add(point)
                }
                segmentIndex++
                currentSegmentSize = 0
                if (additionalPointCount < remainder) {
                    additionalPointCount++
                }
            }
        }
        return segmentMap
    }

    private fun calculateMiddlePoint(points: List<Point>): String {
        if (points.isEmpty()) {
            throw IllegalArgumentException("Список точек не может быть пустым")
        }
        val sum = points.fold(Point(0.0, 0.0)) { acc, point ->
            Point(acc.latitude + point.latitude, acc.longitude + point.longitude)
        }
        val averageLat = sum.latitude / points.size
        val averageLon = sum.longitude / points.size
        return "$averageLat,$averageLon"
    }

    fun getWeather() {
        GetWeatherUseCase()(_screenState.value.sectors.values.map(::calculateMiddlePoint)).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val weatherList = result.data.map(Weather::toWeatherItem)
                    _screenState.update { state ->
                        state.copy(
                            weatherList = weatherList,
                            isLoading = false,
                            indexSnow = weatherList
                                .mapIndexedNotNull { index, weather ->
                                    if (weather.forecastDay[0].day.willItSnow == 1) index else null
                                },
                            indexRain = weatherList
                                .mapIndexedNotNull { index, weather ->
                                    if (weather.forecastDay[0].day.willItRain == 1) index else null
                                },

                            )
                    }
                }

                is Resource.Error -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _screenState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onDialog(index: Int) {
        _screenState.update {
            it.copy(
                onDialog = true,
                indexDialog = index
            )
        }
    }

    fun closedDialog() {
        _screenState.update {
            it.copy(
                onDialog = false,
                indexDialog = null
            )
        }
    }
}
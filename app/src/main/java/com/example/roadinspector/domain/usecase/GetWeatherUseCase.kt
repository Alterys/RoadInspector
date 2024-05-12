package com.example.roadinspector.domain.usecase

import com.example.roadinspector.common.Resource
import com.example.roadinspector.data.remote.response.weather.Weather
import com.example.roadinspector.data.repository.WeatherRepositoryImpl
import com.example.roadinspector.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherUseCase(
    private val repository: WeatherRepository = WeatherRepositoryImpl
) {
    operator fun invoke(q: List<String>): Flow<Resource<List<Weather>>> = flow {
        try {
            emit(Resource.Loading)
            val response = repository.getWeather(q)
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            e.printStackTrace()
        }
    }
}
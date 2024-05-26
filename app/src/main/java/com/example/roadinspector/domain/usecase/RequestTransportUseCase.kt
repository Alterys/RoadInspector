package com.example.roadinspector.domain.usecase

import com.example.roadinspector.common.Resource
import com.example.roadinspector.data.repository.RequestTransportRepositoryImpl
import com.example.roadinspector.domain.model.ResultRequestTransport
import com.example.roadinspector.domain.repository.RequestTransportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RequestTransportUseCase(
    private val repository: RequestTransportRepository = RequestTransportRepositoryImpl
) {
    operator fun invoke(
        email: String,
        comment: String,
        latitude: Double,
        longitude: Double
    ): Flow<Resource<ResultRequestTransport>> = flow {
        try {
            emit(Resource.Loading)
            val response = repository.requestTransport(email, comment, latitude, longitude)
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}
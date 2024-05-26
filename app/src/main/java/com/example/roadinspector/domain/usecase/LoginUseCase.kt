package com.example.roadinspector.domain.usecase

import com.example.roadinspector.common.Resource
import com.example.roadinspector.data.repository.AuthRepositoryImpl
import com.example.roadinspector.domain.model.Login
import com.example.roadinspector.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val repository: LoginRepository = AuthRepositoryImpl
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Login>> = flow {
        try {
            emit(Resource.Loading)
            val response = repository.login(email, password)
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}
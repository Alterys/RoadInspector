package com.example.roadinspector.data.repository

import android.util.Log
import com.example.roadinspector.data.remote.request.LoginRequest
import com.example.roadinspector.domain.model.Login
import com.example.roadinspector.domain.repository.LoginRepository

object AuthRepositoryImpl : LoginRepository {
    private val api = ApiModule.myApi

    override suspend fun login(email: String, password: String): Login {
        val result = api.login(LoginRequest(email, password))
        return Login(
            message = result.message,
            isLoggedIn = result.token != null && result.message == "Login successfully"
        )
    }
}
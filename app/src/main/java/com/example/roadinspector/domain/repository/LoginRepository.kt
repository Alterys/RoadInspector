package com.example.roadinspector.domain.repository

import com.example.roadinspector.domain.model.Login

interface LoginRepository {
    suspend fun login(email: String, password: String): Login
}
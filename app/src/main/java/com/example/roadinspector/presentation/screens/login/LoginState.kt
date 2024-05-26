package com.example.roadinspector.presentation.screens.login

data class LoginState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val isLoggedIn: Boolean = true
)
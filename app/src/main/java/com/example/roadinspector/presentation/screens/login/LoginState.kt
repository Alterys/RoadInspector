package com.example.roadinspector.presentation.screens.login

data class LoginState(
    val user: String = "",
    val userError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val acceptedLogin: Boolean = false,
    val errorLogin: String? = null
)
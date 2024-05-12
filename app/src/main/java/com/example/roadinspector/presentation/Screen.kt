package com.example.roadinspector.presentation

sealed class Screen(val rout: String) {
    data object Login : Screen("login")
    data object Map : Screen("map")
}
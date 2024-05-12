package com.example.roadinspector.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.example.roadinspector.presentation.screens.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel: ViewModel()  {

    private val _screenState = MutableStateFlow(LoginState())
    val screenState: StateFlow<LoginState> = _screenState

    fun login(login: String, password: String) {

    }
}
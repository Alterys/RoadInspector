package com.example.roadinspector.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadinspector.common.Resource
import com.example.roadinspector.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(LoginState())
    val screenState: StateFlow<LoginState> = _screenState

    fun login(email: String, password: String) {
        LoginUseCase()(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _screenState.update { state ->
                        state.copy(
                            message = result.data.message,
                            isLoggedIn = result.data.isLoggedIn,
                            isLoading = false
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

    fun exit() {
        _screenState.update { state ->
            state.copy(
                isLoggedIn = false,
                message = null
            )
        }
    }
}

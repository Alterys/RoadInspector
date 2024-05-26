package com.example.roadinspector.presentation.screens.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadinspector.common.Resource
import com.example.roadinspector.domain.usecase.RequestTransportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class RequestTransportViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(RequestTransportState())
    val screenState: StateFlow<RequestTransportState> = _screenState

    fun requestTransport(email: String, comment: String, coordinate: String) {
        val parts = coordinate.split(",")
        val latitude = parts[0].toDouble()
        val longitude = parts[1].toDouble()
        RequestTransportUseCase()(email, comment, latitude, longitude).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _screenState.update { state ->
                        state.copy(
                            message = result.data.message,
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
        _screenState.update {
            it.copy(
                message = null
            )
        }
    }
}

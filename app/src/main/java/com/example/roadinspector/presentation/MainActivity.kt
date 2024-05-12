package com.example.roadinspector.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.roadinspector.common.secret.Secret
import com.example.roadinspector.presentation.screens.login.LoginScreen
import com.example.roadinspector.presentation.screens.login.LoginViewModel
import com.example.roadinspector.presentation.screens.map.MapScreen
import com.example.roadinspector.presentation.screens.map.MapViewModel
import com.example.universitywork.presentation.ui.theme.RoadInspectorTheme
import com.yandex.mapkit.MapKitFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(Secret.API_KEY)
        MapKitFactory.initialize(this)

        setContent {
            RoadInspectorTheme {
                val mapViewModel: MapViewModel by viewModels()
                val loginViewModel: LoginViewModel by viewModels()
                val stateMap = mapViewModel.screenState.collectAsState().value
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.rout
                ) {
                    composable(Screen.Login.rout) {
                        LoginScreen(
                            navController = navController,
                            login = loginViewModel::login
                        )
                    }
                    composable(Screen.Map.rout) {
                        MapScreen(
                            navController = navController,
                            screenState = stateMap,
                            getWeather = mapViewModel::getWeather,
                            closedDialog = mapViewModel::closedDialog,
                            onDialog = mapViewModel::onDialog
                        )
                    }
                }
            }
        }
    }
}


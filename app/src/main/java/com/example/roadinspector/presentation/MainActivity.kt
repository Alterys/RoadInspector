package com.example.roadinspector.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.roadinspector.common.secret.Secret
import com.example.roadinspector.presentation.screens.login.LoginScreen
import com.example.roadinspector.presentation.screens.login.LoginViewModel
import com.example.roadinspector.presentation.screens.map.MapScreen
import com.example.roadinspector.presentation.screens.map.MapViewModel
import com.example.roadinspector.presentation.screens.request.RequestTransportScreen
import com.example.roadinspector.presentation.screens.request.RequestTransportViewModel
import com.example.roadinspector.presentation.ui.theme.RoadInspectorTheme
import com.yandex.mapkit.MapKitFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(Secret.API_KEY)
        MapKitFactory.initialize(this)

        setContent {
            RoadInspectorTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.rout
                ) {
                    composable(Screen.Login.rout) {
                        val loginViewModel: LoginViewModel by viewModels()
                        LoginScreen(
                            navController = navController,
                            login = loginViewModel::login,
                            screenState = loginViewModel.screenState.collectAsState().value,
                            exit = loginViewModel::exit
                        )
                    }
                    composable(Screen.Map.rout) {
                        val mapViewModel: MapViewModel by viewModels()
                        MapScreen(
                            onNavigateToSpecialRequest = {
                                navController.navigate("request_transport/$it") {
                                    popUpTo("map")
                                }
                            },
                            navController = navController,
                            screenState = mapViewModel.screenState.collectAsState().value,
                            getWeather = mapViewModel::getWeather,
                            closedDialog = mapViewModel::closedDialog,
                            onDialog = mapViewModel::onDialog
                        )
                    }
                    composable(
                        route = "request_transport/{coordinates}",
                        arguments = listOf(navArgument("coordinates") { type = NavType.StringType })
                    ) {
                        val requestTransportViewModel: RequestTransportViewModel by viewModels()
                        val coordinates = it.arguments?.getString("coordinates") ?: ""
                        RequestTransportScreen(
                            coordinates = coordinates,
                            navController = navController,
                            screenState = requestTransportViewModel.screenState.collectAsState().value,
                            requestTransport = requestTransportViewModel::requestTransport,
                            exit = requestTransportViewModel::exit
                        )
                    }
                }
            }
        }
    }
}


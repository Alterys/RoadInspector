package com.example.roadinspector.presentation.screens.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.universitywork.R
import com.example.roadinspector.presentation.Screen
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavHostController,
    screenState: MapState,
    getWeather: () -> Unit,
    onDialog: (Int) -> Unit,
    closedDialog: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val items = listOf("данные", "выход")
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf(items[0]) }
    val polyLines = remember { mutableListOf<PolylineMapObject>() }
    getWeather()
    if (screenState.weatherList?.isNotEmpty() == true) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    items.forEach { item ->
                        TextButton(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    drawerState.isClosed
                                }
                                when (item) {
                                    "выход" -> {
                                        navController.navigate(Screen.Login.rout)
                                    }

                                    "данные" -> {}
                                }
                            },
                        ) { Text(item, fontSize = 22.sp) }
                    }
                }
            },
            content = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            title = {
                                Row {
                                    IconButton(onClick = { scope.launch { drawerState.open() } },
                                        content = { Icon(Icons.Filled.Menu, "Меню") }
                                    )
                                    Button(
                                        onClick = {
                                            getWeather()
                                        }
                                    ) {
                                        Text("обновить погоду")
                                    }

                                }
                            },

                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.White,
                                titleContentColor = Color.Black
                            )
                        )
                    },
                ) { _ ->
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            polyLines.clear()
                            MapView(context).apply {
                                MapKitFactory.getInstance().onStart()
                                this.map.move(
                                    CameraPosition(
                                        Point(50.367402, 40.139646), 6.5f, 0.0f, 0.0f
                                    )
                                )
                                screenState.sectors.forEach { (index, sectorPoints) ->
                                    val sectorPolyline =
                                        map.mapObjects.addPolyline(Polyline(sectorPoints))
                                    polyLines.add(sectorPolyline)
                                    sectorPolyline.apply {
                                        strokeWidth = 5f
                                        outlineWidth = 1f
                                        setStrokeColor(
                                            ContextCompat.getColor(
                                                context,
                                                R.color.grey
                                            )
                                        )
                                        outlineColor =
                                            ContextCompat.getColor(context, R.color.black)
                                    }
                                    screenState.weatherList.takeIf { list ->
                                        list.isNotEmpty()
                                    }?.let {
                                        sectorPolyline.addTapListener { _, _ ->
                                            onDialog(index)
                                            true
                                        }
                                    }
                                }


                            }
                        },
                        update = { it ->
                            it.apply {
                                screenState.weatherList.takeIf { list ->
                                    list.isNotEmpty()
                                }?.let {
                                    screenState.sectors.forEach { (index, sectorPoints) ->
                                        val sectorPolyline =
                                            map.mapObjects.addPolyline(Polyline(sectorPoints))
                                        val color = when {
                                            screenState.indexRain.contains(index) -> R.color.blue
                                            screenState.indexSnow.contains(index) -> R.color.white
                                            else -> R.color.grey
                                        }
                                        sectorPolyline.apply {
                                            strokeWidth = 5f
                                            outlineWidth = 1f
                                            setStrokeColor(ContextCompat.getColor(context, color))
                                            outlineColor =
                                                ContextCompat.getColor(context, R.color.black)
                                        }

                                        sectorPolyline.addTapListener { _, _ ->
                                            onDialog(index)
                                            true
                                        }
                                    }
                                }
                            }
                        }
                    )

                }
            }
        )
    } else {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box {
                CircularProgressIndicator()
            }
        }
    }
    if (screenState.onDialog) {
        Dialog(
            onDismissRequest = {}
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    screenState.indexDialog?.let {
                        val today = screenState.weatherList?.get(it)?.forecastDay?.get(0)
                        val tomorrow = screenState.weatherList?.get(it)?.forecastDay?.get(1)
                        val current = screenState.weatherList?.get(it)?.current
                        if (today != null && tomorrow != null && current != null) {
                            val windSpeedToday =
                                BigDecimal(today.day.maxwindKph * (1000.0 / 3600.0))
                                    .setScale(2, RoundingMode.HALF_UP)
                            val windSpeedTomorrow =
                                BigDecimal(tomorrow.day.maxwindKph * (1000.0 / 3600.0))
                                    .setScale(2, RoundingMode.HALF_UP)
                            val willItRainTomorrow = when (tomorrow.day.willItRain) {
                                1 -> stringResource(R.string.yes)
                                0 -> stringResource(R.string.no)
                                else -> stringResource(R.string.indefinitely)
                            }
                            val willItSnowTomorrow = when (tomorrow.day.willItSnow) {
                                1 -> stringResource(R.string.yes)
                                0 -> stringResource(R.string.no)
                                else -> stringResource(R.string.indefinitely)
                            }
                            val willItRainToday = when (today.day.willItRain) {
                                1 -> stringResource(R.string.yes)
                                0 -> stringResource(R.string.no)
                                else -> stringResource(R.string.indefinitely)
                            }
                            val willItSnowToday = when (today.day.willItSnow) {
                                1 -> stringResource(R.string.yes)
                                0 -> stringResource(R.string.no)
                                else -> stringResource(R.string.indefinitely)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Участок #${it + 1}",
                                    textAlign = TextAlign.Center
                                )
                            }

                            Box(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Column {
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.data) + " " + today.date
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.status) + " " + today.day.condition.textForecastCondition
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.current_status) + "\n" + current.condition.textCurrentCondition
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.will_it_rain) + " " + willItRainToday
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.will_it_snow) + " " + willItSnowToday
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.wind_speed) + " " + windSpeedToday + " " + stringResource(
                                            R.string.meters_per_second
                                        )
                                    )
                                    if (today.day.willItRain == 1 || today.day.willItSnow == 1) {
                                        Text(
                                            modifier = Modifier.padding(2.dp),
                                            text = stringResource(id = R.string.precipitation) + " " + today.day.totalPrecipMm + " " + stringResource(
                                                id = R.string.millimeters
                                            )
                                        )
                                    }
                                    if (today.day.willItSnow == 1) {
                                        Text(
                                            modifier = Modifier.padding(2.dp),
                                            text = stringResource(id = R.string.snowfall_in_cm) + " " + today.day.totalSnowCm + " " + stringResource(
                                                id = R.string.centimeters
                                            )
                                        )
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Column {
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.data) + " " + tomorrow.date
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.status) + " " + tomorrow.day.condition.textForecastCondition
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.will_it_rain) + " " + willItRainTomorrow
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.will_it_snow) + " " + willItSnowTomorrow
                                    )
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(id = R.string.wind_speed) + " " + windSpeedTomorrow + " " + stringResource(
                                            R.string.meters_per_second
                                        )
                                    )
                                    if (tomorrow.day.willItRain == 1 || tomorrow.day.willItSnow == 1) {
                                        Text(
                                            modifier = Modifier.padding(2.dp),
                                            text = stringResource(id = R.string.precipitation) + " " + tomorrow.day.totalPrecipMm + " " + stringResource(
                                                id = R.string.millimeters
                                            )
                                        )
                                    }
                                    if (tomorrow.day.willItSnow == 1) {
                                        Text(
                                            modifier = Modifier.padding(2.dp),
                                            text = stringResource(id = R.string.snowfall_in_cm) + " " + tomorrow.day.totalSnowCm + " " + stringResource(
                                                id = R.string.centimeters
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { closedDialog() }
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

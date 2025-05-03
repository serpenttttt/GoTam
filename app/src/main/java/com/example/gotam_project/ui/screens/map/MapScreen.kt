package com.example.gotam_project.ui.screens.map

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView as YandexMapView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    navController: androidx.navigation.NavController,
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val currentLocation by viewModel.currentLocation.collectAsState()

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            viewModel.startTracking()
        } else {
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }

    currentLocation?.let { point ->
        AndroidView(factory = {
            YandexMapView(context).apply {
                map.move(
                    CameraPosition(point, 16.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f), null
                )
                map.mapObjects.clear()  // Очищаем старые метки
                map.mapObjects.addPlacemark(point)
            }
        }, modifier = Modifier.fillMaxSize())
    }
}
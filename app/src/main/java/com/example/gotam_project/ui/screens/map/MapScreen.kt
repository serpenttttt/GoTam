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
import com.yandex.mapkit.geometry.Polyline

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

    val path by viewModel.routePoints.collectAsState()

    currentLocation?.let { point ->
        AndroidView(factory = {
            YandexMapView(context).apply {
                map.move(CameraPosition(point, 16f, 0f, 0f), Animation(Animation.Type.SMOOTH, 1f), null)
                map.mapObjects.clear()
                map.mapObjects.addPlacemark(point)

                if (path.size >= 2) {
                    map.mapObjects.addPolyline(Polyline(path))
                }
            }
        }, modifier = Modifier.fillMaxSize())
    }
}
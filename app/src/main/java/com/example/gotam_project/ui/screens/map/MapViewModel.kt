package com.example.gotam_project.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotam_project.util.location.LocationClient
import com.example.gotam_project.util.sensors.StepCounterManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationClient: LocationClient,
    private val stepCounterManager: StepCounterManager
) : ViewModel() {

    private val _routePoints = MutableStateFlow<List<Point>>(emptyList())
    val routePoints = _routePoints.asStateFlow()

    private val _currentLocation = MutableStateFlow<Point?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    val stepCount: StateFlow<Int> = stepCounterManager.stepCount

    private var locationJob: Job? = null

    fun startTracking() {
        if (locationJob != null) return
        stepCounterManager.start()
        locationJob = viewModelScope.launch {
            locationClient.getLocationUpdates()
                .collect { loc ->
                    val point = Point(loc.latitude, loc.longitude)
                    _routePoints.update { it + point }
                    _currentLocation.value = point
                }
        }
    }

    fun stopTracking() {
        locationJob?.cancel()
        locationJob = null
        _routePoints.value = emptyList()
        _currentLocation.value = null
        stepCounterManager.stop()
    }

    fun calculateCalories(weightKg: Double): Double {
        // Допустим, 0.04 ккал на шаг
        return stepCount.value * 0.04 * (weightKg / 53.0)
    }
}
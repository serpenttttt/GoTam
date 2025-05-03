package com.example.gotam_project.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotam_project.util.sensors.StepCounterManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val stepCounterManager: StepCounterManager
) : ViewModel() {

    private val _routePoints = MutableStateFlow<List<Point>>(emptyList())
    val routePoints = _routePoints.asStateFlow()

    private val _currentLocation = MutableStateFlow<Point?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    val stepCount: StateFlow<Int> = stepCounterManager.stepCount

    fun startTracking() {
        viewModelScope.launch {
            simulateLocationUpdates()
        }
        stepCounterManager.start()
    }

    private suspend fun simulateLocationUpdates() {
        repeat(100) {
            val newPoint = Point(55.751574 + it * 0.0005, 37.573856 + it * 0.0005)
            _routePoints.update { currentPoints -> currentPoints + newPoint }
            _currentLocation.value = newPoint
            delay(1000)
        }
    }

    fun stopTracking() {
        _routePoints.value = emptyList()
        _currentLocation.value = null
        stepCounterManager.stop()
    }
}
package com.example.gotam_project.ui.screens.walk

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotam_project.domain.model.WalkDTO
import com.example.gotam_project.domain.usecase.GetWalksUsecase
import com.example.gotam_project.domain.usecase.SaveWalkUsecase
import com.example.gotam_project.util.location.LocationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(
    private val locationClient: LocationClient,
    private val saveWalkUsecase: SaveWalkUsecase,
    private val getWalksUsecase: GetWalksUsecase
) : ViewModel() {

    private val _path = MutableStateFlow<List<Location>>(emptyList())
    val path: StateFlow<List<Location>> = _path

    private val _walks = MutableStateFlow<List<WalkDTO>>(emptyList())
    val walks: StateFlow<List<WalkDTO>> = _walks

    init {
        // Сразу начинаем собирать сохранённые прогулки
        viewModelScope.launch {
            getWalksUsecase().collect {
                _walks.value = it
            }
        }
    }

    fun startTracking() {
        viewModelScope.launch {
            locationClient.getLocationUpdates().collectLatest { location ->
                _path.value = _path.value + location
            }
        }
    }

    fun saveWalk(steps: Int, duration: Long) {
        viewModelScope.launch {
            val calories = (steps * 0.04).toInt()
            val encodedPath = _path.value.joinToString(";") { "${it.latitude},${it.longitude}" }
            val walk = WalkDTO(
                date = System.currentTimeMillis(),
                duration = duration,
                steps = steps,
                path = encodedPath,
                calories = calories
            )
            saveWalkUsecase(walk)
        }
    }
}
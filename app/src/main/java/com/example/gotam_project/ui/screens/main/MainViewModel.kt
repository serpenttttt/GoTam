package com.example.gotam_project.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotam_project.domain.model.PetDTO
import com.example.gotam_project.domain.usecase.GetPetUsecase
import com.example.gotam_project.domain.usecase.SetPetNameUsecase
import com.example.gotam_project.domain.usecase.SetWalkTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPet: GetPetUsecase,
    private val setWalkTime: SetWalkTime,
    private val setPetName: SetPetNameUsecase
) : ViewModel() {

    private val _pet = MutableStateFlow<PetDTO?>(null)
    val pet: StateFlow<PetDTO?> get() = _pet

    init {
        loadPet()
    }

    private fun loadPet() {
        viewModelScope.launch {
            try {
                _pet.value = getPet.invoke(1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setWalkTime(newWalkTime: Long) {
        _pet.value?.let { currentPet ->
            val updatedPet = currentPet.copy(walkTime = newWalkTime)
            viewModelScope.launch {
                try {
                    setWalkTime.invoke(updatedPet)
                    _pet.value = updatedPet
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun savePetName(newName: String) {
        _pet.value?.let { currentPet ->
            viewModelScope.launch {
                try {
                    setPetName.invoke(currentPet.id, newName)
                    _pet.value = currentPet.copy(name = newName)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
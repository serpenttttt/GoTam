package com.example.gotam_project.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gotam_project.domain.usecase.GetPetUsecase
import com.example.gotam_project.domain.usecase.SetWalkTime

class MainViewModelFactory(
    private val getPet: GetPetUsecase,
    private val setWalkTime: SetWalkTime
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getPet, setWalkTime) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
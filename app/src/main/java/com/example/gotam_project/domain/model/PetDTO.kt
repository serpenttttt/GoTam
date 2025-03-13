package com.example.gotam_project.domain.model


data class PetDTO(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val walkTime: Int // Время прогулки
)
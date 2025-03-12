package com.example.gotam_project.domain.model

interface IPetRepository {
    suspend fun getPet(): PetDTO
    suspend fun setWalkTime(duration: Int)
}
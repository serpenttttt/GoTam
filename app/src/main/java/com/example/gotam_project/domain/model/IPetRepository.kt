package com.example.gotam_project.domain.model

import com.example.gotam_project.data.room.PetEntity

interface IPetRepository {
    suspend fun getPet(id: Int): PetDTO
    suspend fun insertPet(pet: PetEntity)
    suspend fun setWalkTime(pet: PetDTO)
}
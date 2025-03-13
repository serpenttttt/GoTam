package com.example.gotam_project.data


import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.model.PetDTO
import kotlinx.coroutines.delay

class PetRepositoryImpl : IPetRepository {

    // In-memory данные о питомце
    private var pet = PetDTO(
        id = 1,
        name = "Бобик",
        imageUrl = "https://example.com/pet_image.jpg",
        walkTime = 0
    )

    override suspend fun getPet(): PetDTO {
        // Имитация задержки сети
        delay(1000)
        return pet
    }

    override suspend fun setWalkTime(duration: Int) {
        // Имитация задержки сети
        delay(1000)
        pet = pet.copy(walkTime = duration)
    }
}
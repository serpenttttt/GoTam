package com.example.gotam_project.domain.usecase

import com.example.gotam_project.data.repository.toPetEntity
import com.example.gotam_project.domain.model.IPetRepository
import javax.inject.Inject

class SetPetNameUsecase @Inject constructor(private val repository: IPetRepository) {
    suspend fun invoke(petId: Int, newName: String) {
        val pet = repository.getPet(petId)
        if (pet != null) {
            val updatedPet = pet.copy(name = newName)
            repository.insertPet(updatedPet.toPetEntity())
        }
    }
}
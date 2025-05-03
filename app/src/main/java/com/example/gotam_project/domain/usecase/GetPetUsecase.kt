package com.example.gotam_project.domain.usecase

import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.model.PetDTO

class GetPetUsecase(private val repository: IPetRepository) {
    suspend operator fun invoke(id: Int): PetDTO {
        return repository.getPet(id) ?: throw IllegalArgumentException("Pet with id $id not found")
    }
}
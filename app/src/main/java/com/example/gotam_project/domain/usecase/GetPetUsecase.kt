package com.example.gotam_project.domain.usecase

import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.model.PetDTO

class GetPetUseCase(private val repository: IPetRepository) {
    suspend fun execute(): PetDTO {
        return repository.getPet()
    }
}
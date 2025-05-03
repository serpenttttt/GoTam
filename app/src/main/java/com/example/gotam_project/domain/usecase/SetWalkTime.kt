package com.example.gotam_project.domain.usecase

import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.model.PetDTO

class SetWalkTime(private val petRepository: IPetRepository) {

    suspend operator fun invoke(pet: PetDTO) {
        petRepository.setWalkTime(pet)
    }
}
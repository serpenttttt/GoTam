package com.example.gotam_project.domain.usecase

import com.example.gotam_project.domain.model.IWalkRepository
import com.example.gotam_project.domain.model.WalkDTO
import kotlinx.coroutines.flow.Flow

class GetWalksUsecase(
    private val walkRepository: IWalkRepository
) {
    suspend operator fun invoke(): Flow<List<WalkDTO>> {
        return walkRepository.getAllWalks() // Запрашиваем все прогулки из репозитория
    }
}
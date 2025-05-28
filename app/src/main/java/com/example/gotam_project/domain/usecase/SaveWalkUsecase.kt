package com.example.gotam_project.domain.usecase

import com.example.gotam_project.domain.model.IWalkRepository
import com.example.gotam_project.domain.model.WalkDTO

class SaveWalkUsecase(private val repository: IWalkRepository) {
    suspend operator fun invoke(walk: WalkDTO) {
        repository.insertWalk(walk)
    }
}
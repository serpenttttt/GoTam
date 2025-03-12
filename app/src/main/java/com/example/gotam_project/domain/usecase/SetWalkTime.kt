package com.example.gotam_project.domain.usecase

import com.example.gotam_project.domain.model.IPetRepository


class SetWalkTime(private val repository: IPetRepository) {
    suspend fun execute(duration: Int) {
        repository.setWalkTime(duration)
    }
}
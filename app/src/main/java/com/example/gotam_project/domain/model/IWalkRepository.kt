package com.example.gotam_project.domain.model

import com.example.gotam_project.domain.model.WalkDTO
import kotlinx.coroutines.flow.Flow

interface IWalkRepository {
    suspend fun insertWalk(walk: WalkDTO)
    suspend fun getAllWalks(): Flow<List<WalkDTO>>
}
package com.example.gotam_project.data.repository

import com.example.gotam_project.data.room.WalkDao
import com.example.gotam_project.data.room.WalkEntity
import com.example.gotam_project.domain.model.IWalkRepository
import com.example.gotam_project.domain.model.WalkDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalkRepositoryImpl(
    private val walkDao: WalkDao
) : IWalkRepository {

    override suspend fun insertWalk(walk: WalkDTO) {
        walkDao.insertWalk(walk.toEntity())
    }

    override suspend fun getAllWalks(): Flow<List<WalkDTO>> {
        // Используем чтобы преобразовать WalkEntity в WalkDTO
        return walkDao.getAllWalks().map { walkEntities ->
            walkEntities.map { walkEntity -> walkEntity.toDTO() }
        }
    }
}

// --- Mapping Extension Functions ---
private fun WalkEntity.toDTO(): WalkDTO {
    return WalkDTO(
        id = this.id,
        date = this.date,
        duration = this.duration,
        steps = this.steps,
        path = this.path,
        calories = this.calories
    )
}

private fun WalkDTO.toEntity(): WalkEntity {
    return WalkEntity(
        id = this.id,
        date = this.date,
        duration = this.duration,
        steps = this.steps,
        path = this.path,
        calories = this.calories
    )
}
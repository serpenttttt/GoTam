package com.example.gotam_project.data.repository

import com.example.gotam_project.data.room.PetDao
import com.example.gotam_project.data.room.PetEntity
import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.model.PetDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetRepositoryImpl(
    private val petDao: PetDao
) : IPetRepository {

    init {
        initializeData()
    }

    private fun initializeData() {
        CoroutineScope(Dispatchers.IO).launch {
            if (petDao.getPetById(1) == null) {
                val initialPet = PetEntity(
                    id = 1,
                    name = "Tam",
                    walkTime = 0
                )
                petDao.insertPet(initialPet)
            }
        }
    }

    override suspend fun getPet(id: Int): PetDTO {
        val petEntity = petDao.getPetById(id)
            ?: throw IllegalArgumentException("Pet with id $id not found")
        return PetDTO(
            id = petEntity.id,
            name = petEntity.name,
            walkTime = petEntity.walkTime,
        )
    }

    override suspend fun insertPet(pet: PetEntity) {
        petDao.insertPet(pet)
    }

    override suspend fun setWalkTime(pet: PetDTO) {
        val petEntity = PetEntity(
            id = pet.id,
            name = pet.name,
            walkTime = pet.walkTime
        )
        petDao.updateWalkTime(petEntity)
    }
}
package com.example.gotam_project.data


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

    fun initializeData(coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.IO) {
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

    override suspend fun getPet(id: Int): PetDTO? {
        val petEntity = petDao.getPetById(id)
        return petEntity?.toPetDTO()
    }

    override suspend fun insertPet(pet: PetEntity) {
        petDao.insertPet(pet)
    }

    override suspend fun setWalkTime(pet: PetDTO) {
        petDao.updateWalkTime(pet.id, pet.walkTime)
    }

    override suspend fun setPetName(id: Int, name: String) {
        petDao.updatePetName(id, name)
    }
}

// Расширения для преобразования между PetEntity и PetDTO
fun PetEntity.toPetDTO(): PetDTO {
    return PetDTO(
        id = this.id,
        name = this.name,
        walkTime = this.walkTime
    )
}

fun PetDTO.toPetEntity(): PetEntity {
    return PetEntity(
        id = this.id,
        name = this.name,
        walkTime = this.walkTime
    )
}
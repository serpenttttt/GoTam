package com.example.gotam_project.data.repository

import com.example.gotam_project.data.room.PetDao
import com.example.gotam_project.data.room.PetEntity
import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.model.PetDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetRepositoryImpl(
    private val petDao: PetDao,
    coroutineScope: CoroutineScope
) : IPetRepository {

    // Инициализация данных, если питомец еще не создан
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

    // Получение питомца по ID
    override suspend fun getPet(id: Int): PetDTO? {
        val petEntity = petDao.getPetById(id)
        return petEntity?.toPetDTO()
    }

    // Вставка нового питомца
    override suspend fun insertPet(pet: PetEntity) {
        petDao.insertPet(pet)
    }

    // Обновление времени прогулки питомца
    override suspend fun setWalkTime(pet: PetDTO) {
        petDao.updateWalkTime(pet.id, pet.walkTime)
    }

    // Обновление имени питомца
    override suspend fun setPetName(id: Int, name: String) {
        petDao.updatePetName(id, name)
    }
}

// Расширение для преобразования из PetEntity в PetDTO
fun PetEntity.toPetDTO(): PetDTO {
    return PetDTO(
        id = this.id,
        name = this.name,
        walkTime = this.walkTime
    )
}

// Расширение для преобразования из PetDTO в PetEntity
fun PetDTO.toPetEntity(): PetEntity {
    return PetEntity(
        id = this.id,
        name = this.name,
        walkTime = this.walkTime
    )
}
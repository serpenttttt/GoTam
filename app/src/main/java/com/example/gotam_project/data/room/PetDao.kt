package com.example.gotam_project.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// Data Access Object для работы с таблицей питомцев
@Dao
interface PetDao {

    // Получение питомца по ID
    @Query("SELECT * FROM pet_table WHERE id = :id")
    suspend fun getPetById(id: Int): PetEntity?

    // Вставка нового питомца, при конфликте заменяет существующую запись
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    // Обновление данных питомца
    @Update
    suspend fun updatePet(pet: PetEntity)

    // Обновление времени прогулки питомца по ID
    @Query("UPDATE pet_table SET walk_time = :walkTime WHERE id = :id")
    suspend fun updateWalkTime(id: Int, walkTime: Long)

    // Обновление имени питомца по ID
    @Query("UPDATE pet_table SET name = :name WHERE id = :id")
    suspend fun updatePetName(id: Int, name: String)
}
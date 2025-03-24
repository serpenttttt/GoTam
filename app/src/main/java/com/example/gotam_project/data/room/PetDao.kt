package com.example.gotam_project.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PetDao {
    @Query("SELECT * FROM pet_table WHERE id = :id")
    suspend fun getPetById(id: Int): PetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    @Update
    suspend fun updatePet(pet: PetEntity)

    @Query("UPDATE pet_table SET walk_time = :walkTime WHERE id = :id")
    suspend fun updateWalkTime(id: Int, walkTime: Long)

    @Query("UPDATE pet_table SET name = :name WHERE id = :id")
    suspend fun updatePetName(id: Int, name: String)
}
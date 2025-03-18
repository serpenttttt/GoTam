package com.example.gotam_project.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PetDao {

    @Query("SELECT * FROM pet WHERE id = :id")
    suspend fun getPetById(id: Int): PetEntity?

    @Insert
    suspend fun insertPet(pet: PetEntity)

    @Update
    suspend fun updateWalkTime(pet: PetEntity)
}
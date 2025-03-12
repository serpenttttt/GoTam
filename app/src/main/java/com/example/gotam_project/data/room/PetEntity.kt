package com.example.gotam_project.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val walkTime: Int
)
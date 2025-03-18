package com.example.gotam_project.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet")
data class PetEntity(
    @PrimaryKey val id: Int,
    val name: String,
    var walkTime: Long
)
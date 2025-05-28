package com.example.gotam_project.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Сущность для хранения данных о питомце в базе данных
@Entity(tableName = "pet_table")
data class PetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int, // генерируется автоматически
    @ColumnInfo(name = "name") val name: String, // Имя питомца
    @ColumnInfo(name = "walk_time") val walkTime: Long // Время прогулки в миллисекундах
)
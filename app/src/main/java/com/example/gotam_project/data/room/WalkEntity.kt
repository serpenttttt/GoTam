package com.example.gotam_project.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "walk_table")
data class WalkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val duration: Long,
    val steps: Int,
    val path: String,
    val calories: Int
)
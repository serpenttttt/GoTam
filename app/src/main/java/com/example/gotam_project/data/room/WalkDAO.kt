package com.example.gotam_project.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WalkDao {
    @Insert
    suspend fun insertWalk(walk: WalkEntity)

    @Query("SELECT * FROM walk_table ORDER BY date DESC")
    fun getAllWalks(): Flow<List<WalkEntity>> // Возвращаем все прогулки
}
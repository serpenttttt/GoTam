package com.example.gotam_project.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PetEntity::class, WalkEntity::class],
    version = 4, // увеличиваем версию, т.к. добавили новую таблицу
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao
    abstract fun walkDao(): WalkDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pet_database"
                )
                    .fallbackToDestructiveMigration() // пусть остаётся пока
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
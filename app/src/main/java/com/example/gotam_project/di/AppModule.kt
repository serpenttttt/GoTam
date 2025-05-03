package com.example.gotam_project.di

import android.content.Context
import com.example.gotam_project.data.room.AppDatabase
import com.example.gotam_project.data.room.PetDao
import com.example.gotam_project.data.repository.PetRepositoryImpl
import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.usecase.GetPetUsecase
import com.example.gotam_project.domain.usecase.SetPetNameUsecase
import com.example.gotam_project.domain.usecase.SetWalkTime
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providePetDao(appDatabase: AppDatabase): PetDao {
        return appDatabase.petDao()
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun providePetRepository(
        petDao: PetDao,
        coroutineScope: CoroutineScope // Добавляем CoroutineScope
    ): IPetRepository {
        return PetRepositoryImpl(petDao)
    }

    @Provides
    @Singleton
    fun provideGetPetUsecase(petRepository: IPetRepository): GetPetUsecase {
        return GetPetUsecase(petRepository)
    }

    @Provides
    @Singleton
    fun provideSetWalkTime(petRepository: IPetRepository): SetWalkTime {
        return SetWalkTime(petRepository)
    }

    @Provides
    @Singleton
    fun provideSetNameUsecase(petRepository: IPetRepository): SetPetNameUsecase {
        return SetPetNameUsecase(petRepository)
    }
}
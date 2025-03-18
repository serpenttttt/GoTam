package com.example.gotam_project.di

import android.content.Context
import com.example.gotam_project.data.room.AppDatabase
import com.example.gotam_project.data.room.PetDao
import com.example.gotam_project.data.repository.PetRepositoryImpl
import com.example.gotam_project.domain.model.IPetRepository
import com.example.gotam_project.domain.usecase.GetPetUsecase
import com.example.gotam_project.domain.usecase.SetWalkTime
import com.example.gotam_project.ui.screens.main.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun providePetRepository(petDao: PetDao): IPetRepository {
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
    fun provideMainViewModelFactory(
        getPetUsecase: GetPetUsecase,
        setWalkTime: SetWalkTime
    ): MainViewModelFactory {
        return MainViewModelFactory(getPetUsecase, setWalkTime)
    }
}
package com.example.gotam_project.di

import android.app.Application
import com.example.gotam_project.util.location.DefaultLocationClient
import com.example.gotam_project.util.location.LocationClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationClient(app: Application): LocationClient {
        return DefaultLocationClient(app)
    }
}
package com.example.jetpackcomposecomponents.di

import com.example.jetpackcomposecomponents.entity.ComponentDao
import com.example.jetpackcomposecomponents.repository.ComponentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideComponentRepository(componentDao: ComponentDao) = ComponentRepository(componentDao)
}
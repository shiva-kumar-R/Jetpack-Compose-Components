package com.example.jetpackcomposecomponents.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackcomposecomponents.entity.ComponentDao
import com.example.jetpackcomposecomponents.entity.ComponentDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideComponentDao(componentDatabase: ComponentDatabase): ComponentDao =
        componentDatabase.getComponentDao()

    @Provides
    @Singleton
    fun provideComponentDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ComponentDatabase::class.java,
            "component_database"
        ).fallbackToDestructiveMigration().build()
}
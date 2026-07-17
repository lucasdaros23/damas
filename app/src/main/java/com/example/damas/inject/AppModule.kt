package com.example.damas.inject

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.damas.core.ResourceProvider
import com.example.damas.core.ResourceProviderImpl
import com.example.damas.data.dao.PieceDao
import com.example.damas.data.database.CheckersDatabase
import com.example.damas.data.repository.PieceRepositoryImpl
import com.example.damas.domain.repository.PieceRepository
import com.example.damas.feature.home.HomeUiEvent
import com.example.damas.feature.home.HomeUiState
import com.example.damas.feature.local.LocalUiEvent
import com.example.damas.feature.local.LocalUiState
import com.example.damas.resources.CheckersStrings
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
    fun provideCheckersDatabase(
        @ApplicationContext context: Context
    ): CheckersDatabase {
        return Room.databaseBuilder(
            context,
            CheckersDatabase::class.java,
            "checkers_database"
        ).build()
    }

    @Provides
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providePieceDao(database: CheckersDatabase): PieceDao = database.pieceDao()

    @Provides
    @Singleton
    fun providePieceRepository(pieceDao: PieceDao): PieceRepository = PieceRepositoryImpl(pieceDao)

    @Provides
    fun provideLocalUiState(): LocalUiState = LocalUiState()

    @Provides
    fun provideLocalUiEvent(): LocalUiEvent = LocalUiEvent()

    @Provides
    fun provideHomeUiState(strings: CheckersStrings): HomeUiState = HomeUiState(strings)

    @Provides
    fun provideHomeUiEvent(): HomeUiEvent = HomeUiEvent()

    @Provides
    fun provideResourceProvider(context: Application): ResourceProvider = ResourceProviderImpl(context)
}
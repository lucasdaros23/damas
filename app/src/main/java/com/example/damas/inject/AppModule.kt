package com.example.damas.inject

import android.content.Context
import androidx.room.Room
import com.example.damas.data.dao.PieceDao
import com.example.damas.data.database.CheckersDatabase
import com.example.damas.data.repository.PieceRepositoryImpl
import com.example.damas.domain.repository.PieceRepository
import com.example.damas.feature.home.HomeUiEvent
import com.example.damas.feature.home.HomeUiState
import com.example.damas.feature.local.LocalUiEvent
import com.example.damas.feature.local.LocalUiState
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
    @Singleton
    fun providePieceDao(database: CheckersDatabase): PieceDao {
        return database.pieceDao()
    }

    @Provides
    @Singleton
    fun providePieceRepository(pieceDao: PieceDao): PieceRepository {
        return PieceRepositoryImpl(pieceDao)
    }

    @Provides
    fun provideLocalUiState(): LocalUiState {
        return LocalUiState()
    }

    @Provides
    fun provideLocalUiEvent(): LocalUiEvent {
        return LocalUiEvent()
    }

    @Provides
    fun provideHomeUiState(): HomeUiState {
        return HomeUiState()
    }

    @Provides
    fun provideHomeUiEvent(): HomeUiEvent {
        return HomeUiEvent()
    }
}
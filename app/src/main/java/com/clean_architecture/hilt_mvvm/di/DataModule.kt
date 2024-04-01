package com.clean_architecture.hilt_mvvm.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.clean_architecture.hilt_mvvm.feature.data.data_source.PhotoDao
import com.clean_architecture.hilt_mvvm.feature.data.data_source.PhotoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context, PhotoDatabase::class.java,
            PhotoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDAO(photoDatabase: PhotoDatabase): PhotoDao {
        return photoDatabase.photoDao()
    }
}
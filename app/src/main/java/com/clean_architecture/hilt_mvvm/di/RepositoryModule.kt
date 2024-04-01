package com.clean_architecture.hilt_mvvm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.clean_architecture.hilt_mvvm.feature.data.data_source.PhotoDao
import com.clean_architecture.hilt_mvvm.feature.data.repository.PhotosRepositoryImpl
import com.clean_architecture.hilt_mvvm.feature.domain.repository.PhotoRepository
import com.clean_architecture.hilt_mvvm.feature.data.network.apiService.ApiService
import com.clean_architecture.hilt_mvvm.feature.data.network.apiService.ImagesApiService
import com.clean_architecture.hilt_mvvm.feature.data.network.handler.NetworkHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        photoDao: PhotoDao,
        apiService: ApiService,
        imageService: ImagesApiService,
        networkHandler: NetworkHandler
    ): PhotoRepository {
        return PhotosRepositoryImpl(photoDao, apiService, imageService, networkHandler)
    }

}
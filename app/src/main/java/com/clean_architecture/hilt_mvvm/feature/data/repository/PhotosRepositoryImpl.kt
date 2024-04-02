package com.clean_architecture.hilt_mvvm.feature.data.repository

import com.clean_architecture.hilt_mvvm.core.failure.Failure
import com.clean_architecture.hilt_mvvm.core.functional.Either
import com.clean_architecture.hilt_mvvm.feature.data.data_source.PhotoDao
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import com.clean_architecture.hilt_mvvm.feature.domain.repository.PhotoRepository
import com.clean_architecture.hilt_mvvm.feature.data.network.apiService.ImagesApiService
import com.clean_architecture.hilt_mvvm.feature.data.network.handler.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

class PhotosRepositoryImpl
    @Inject constructor(
        private val dao: PhotoDao,
        private val imageService : ImagesApiService,
        private val networkHandler: NetworkHandler
) : PhotoRepository {

    override fun images(search: String): Either<Failure, ImageResponse> {
        return when(networkHandler.isNetworkAvailable()) {
            true -> request(
                imageService.get(search, 50),
                { it.toImages() },
                ImageResponse.empty
            )
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

    private fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }

}
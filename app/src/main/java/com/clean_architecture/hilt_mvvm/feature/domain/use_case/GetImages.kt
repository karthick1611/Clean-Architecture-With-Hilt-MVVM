package com.clean_architecture.hilt_mvvm.feature.domain.use_case

import com.clean_architecture.hilt_mvvm.core.failure.Failure
import com.clean_architecture.hilt_mvvm.core.functional.Either
import com.clean_architecture.hilt_mvvm.feature.data.repository.PhotosRepositoryImpl
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import com.clean_architecture.hilt_mvvm.feature.use_case.UseCase
import javax.inject.Inject

class GetImages @Inject constructor(private val moviesRepository: PhotosRepositoryImpl) : UseCase<ImageResponse, GetImages.Params>() {

    override suspend fun run(params: Params): Either<Failure, ImageResponse> = moviesRepository.images(params.search)

    data class Params(val search: String)
}
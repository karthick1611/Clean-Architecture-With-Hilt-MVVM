package com.clean_architecture.hilt_mvvm.feature.domain.repository

import com.clean_architecture.hilt_mvvm.core.failure.Failure
import com.clean_architecture.hilt_mvvm.core.functional.Either
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse

interface PhotoRepository {

    fun images(search: String): Either<Failure, ImageResponse>
}
package com.clean_architecture.hilt_mvvm.feature.domain.repository

import com.clean_architecture.hilt_mvvm.core.failure.Failure
import com.clean_architecture.hilt_mvvm.core.functional.Either
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import com.clean_architecture.hilt_mvvm.feature.domain.model.Movie
import com.clean_architecture.hilt_mvvm.feature.domain.model.MovieDetails

interface PhotoRepository {

    fun movies(): Either<Failure, List<Movie>>

    fun movieDetails(movieId: Int): Either<Failure, MovieDetails>

    fun images(search: String): Either<Failure, ImageResponse>
}
/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clean_architecture.hilt_mvvm.feature.presentation.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.clean_architecture.hilt_mvvm.core.platform.BaseViewModel
import com.clean_architecture.hilt_mvvm.feature.domain.model.Movie
import com.clean_architecture.hilt_mvvm.feature.domain.use_case.GetMovies
import com.clean_architecture.hilt_mvvm.feature.use_case.UseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

    private val _movies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val movies: LiveData<List<MovieView>> = _movies

    fun loadMovies() =
        getMovies(UseCase.None(), viewModelScope) { it.fold(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: List<Movie>) {
        _movies.value = movies.map { MovieView(it.id, it.poster) }
    }
}

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
package com.clean_architecture.hilt_mvvm.feature.domain.use_case

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.clean_architecture.hilt_mvvm.core.failure.Failure
import com.clean_architecture.hilt_mvvm.core.functional.Either
import com.clean_architecture.hilt_mvvm.core.navigation.Navigator
import com.clean_architecture.hilt_mvvm.feature.use_case.UseCase
import javax.inject.Inject

class PlayMovie @Inject constructor(
    @ApplicationContext private val context: Context,
    private val navigator: Navigator
) : UseCase<UseCase.None, PlayMovie.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        navigator.openVideo(context, params.url)
        return Either.Right(None())
    }

    data class Params(val url: String)
}

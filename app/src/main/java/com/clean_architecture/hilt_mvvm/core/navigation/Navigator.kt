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
package com.clean_architecture.hilt_mvvm.core.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.clean_architecture.hilt_mvvm.core.credentials.Authenticator
import com.clean_architecture.hilt_mvvm.core.extension.emptyString
import com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery.ImagesActivity
import com.clean_architecture.hilt_mvvm.feature.presentation.movieDetails.MovieDetailsActivity
import com.clean_architecture.hilt_mvvm.feature.presentation.moviesList.MovieView
import com.clean_architecture.hilt_mvvm.feature.presentation.moviesList.MoviesListActivity
import javax.inject.Inject

class Navigator @Inject constructor(private val authenticator: Authenticator) {

    private fun showLogin(context: Context) = context.startActivity(MoviesListActivity.callingIntent(context))

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showMovies(context)
            false -> showLogin(context)
        }
    }

    private fun showMovies(context: Context) = context.startActivity(MoviesListActivity.callingIntent(context))

    fun showImages(context: Context) = context.startActivity(ImagesActivity.callingIntent(context))

    fun showMovieDetails(activity: FragmentActivity, movie: MovieView, navigationExtras: Extras) {
        val intent = MovieDetailsActivity.callingIntent(activity, movie)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    private val videoUrlHttp = "http://www.youtube.com/watch?v="
    private val videoUrlHttps = "https://www.youtube.com/watch?v="

    fun openVideo(context: Context, videoUrl: String) {
        try {
            context.startActivity(createYoutubeIntent(videoUrl))
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
        }
    }

    private fun createYoutubeIntent(videoUrl: String): Intent {
        val videoId = when {
            videoUrl.startsWith(videoUrlHttp) -> videoUrl.replace(videoUrlHttp, emptyString())
            videoUrl.startsWith(videoUrlHttps) -> videoUrl.replace(
                videoUrlHttps,
                emptyString()
            )
            else -> videoUrl
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        intent.putExtra("force_fullscreen", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        return intent
    }

    class Extras(val transitionSharedElement: View)
}



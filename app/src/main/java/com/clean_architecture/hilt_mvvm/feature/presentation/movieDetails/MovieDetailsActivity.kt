package com.clean_architecture.hilt_mvvm.feature.presentation.movieDetails

import android.content.Context
import android.content.Intent
import com.clean_architecture.hilt_mvvm.core.platform.BaseActivity
import com.clean_architecture.hilt_mvvm.core.platform.BaseFragment
import com.clean_architecture.hilt_mvvm.feature.presentation.moviesList.MovieView

class MovieDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "com.fernandocejas.INTENT_PARAM_MOVIE"

        fun callingIntent(context: Context, movie: MovieView) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_MOVIE, movie)
            }
    }

    override fun fragment(): BaseFragment =
        MovieDetailsFragment.forMovie(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE))
}
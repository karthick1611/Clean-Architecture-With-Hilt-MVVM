package com.clean_architecture.hilt_mvvm.feature.presentation.moviesList

import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import com.clean_architecture.hilt_mvvm.core.platform.BaseActivity

@AndroidEntryPoint
class MoviesListActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesListActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
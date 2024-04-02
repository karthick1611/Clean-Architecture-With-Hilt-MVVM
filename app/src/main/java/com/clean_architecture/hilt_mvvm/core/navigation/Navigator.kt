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

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.clean_architecture.hilt_mvvm.core.credentials.Authenticator
import com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery.ImageDetailsView
import com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery.ImagesActivity
import com.clean_architecture.hilt_mvvm.feature.presentation.imageDetails.ImageDetailsActivity
import javax.inject.Inject

class Navigator @Inject constructor(private val authenticator: Authenticator) {

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showImages(context)
            false -> showImages(context)
        }
    }

    private fun showImages(context: Context) = context.startActivity(ImagesActivity.callingIntent(context))

    fun showImageDetails(activity: FragmentActivity, photo: ImageDetailsView, navigationExtras: Extras) {
        val intent = ImageDetailsActivity.callingIntent(activity, photo)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}



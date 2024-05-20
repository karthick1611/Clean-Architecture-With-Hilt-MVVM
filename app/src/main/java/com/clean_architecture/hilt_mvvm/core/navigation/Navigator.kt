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



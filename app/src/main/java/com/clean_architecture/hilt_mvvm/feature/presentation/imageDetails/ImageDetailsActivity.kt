package com.clean_architecture.hilt_mvvm.feature.presentation.imageDetails

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.clean_architecture.hilt_mvvm.core.platform.BaseActivity
import com.clean_architecture.hilt_mvvm.core.platform.BaseFragment
import com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery.ImageDetailsView

class ImageDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_IMAGE = "com.clean_architecture.INTENT_PARAM_IMAGE"

        fun callingIntent(context: Context, image: Parcelable) =
            Intent(context, ImageDetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_IMAGE, image)
            }
    }

    override fun fragment(): BaseFragment = ImageDetailsFragment.forPhoto(intent.getParcelableExtra<Parcelable>(INTENT_EXTRA_PARAM_IMAGE) as ImageDetailsView)
}
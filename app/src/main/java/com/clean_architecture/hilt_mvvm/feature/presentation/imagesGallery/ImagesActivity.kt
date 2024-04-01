package com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery

import android.content.Context
import android.content.Intent
import com.clean_architecture.hilt_mvvm.core.platform.BaseActivity
import com.clean_architecture.hilt_mvvm.core.platform.BaseFragment

class ImagesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, ImagesActivity::class.java)
    }

    override fun fragment(): BaseFragment = ImagesFragment()
}
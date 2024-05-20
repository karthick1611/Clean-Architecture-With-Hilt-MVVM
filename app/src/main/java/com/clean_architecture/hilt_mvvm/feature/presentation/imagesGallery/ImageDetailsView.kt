package com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDetailsView(val id: Int?, val desc: String?, val url: String?) : Parcelable

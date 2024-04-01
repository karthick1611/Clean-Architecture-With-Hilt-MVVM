package com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.clean_architecture.hilt_mvvm.core.platform.BaseViewModel
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import com.clean_architecture.hilt_mvvm.feature.domain.use_case.GetImages
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val images : GetImages
) : BaseViewModel() {

    private val _imageDetails: MutableLiveData<ImageResponse> = MutableLiveData()
    val imageDetails: LiveData<ImageResponse> = _imageDetails

    fun loadImages(search: String) =
        images(GetImages.Params(search), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleImageDetails
            )
        }

    private fun handleImageDetails(images : ImageResponse) {
        _imageDetails.value = images
    }

}
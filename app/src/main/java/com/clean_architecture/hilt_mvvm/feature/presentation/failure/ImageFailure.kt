package com.clean_architecture.hilt_mvvm.feature.presentation.failure

import com.clean_architecture.hilt_mvvm.core.failure.Failure

class ImageFailure {
    class ListNotAvailable : Failure.FeatureFailure()
    class NonExistentImage : Failure.FeatureFailure()
}


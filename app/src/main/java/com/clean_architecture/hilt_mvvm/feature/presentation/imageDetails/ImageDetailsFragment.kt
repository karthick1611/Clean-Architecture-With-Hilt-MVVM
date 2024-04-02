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
package com.clean_architecture.hilt_mvvm.feature.presentation.imageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.clean_architecture.hilt_mvvm.core.extension.loadUrlAndPostponeEnterTransition
import com.clean_architecture.hilt_mvvm.core.platform.BaseActivity
import com.clean_architecture.hilt_mvvm.core.platform.BaseFragment
import com.clean_architecture.hilt_mvvm.databinding.FragmentImageDetailsBinding
import com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery.ImageDetailsView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_PHOTO = "param_photo"

        fun forPhoto(photo: ImageDetailsView?) = ImageDetailsFragment().apply {
            arguments = bundleOf(PARAM_PHOTO to photo)
        }
    }

    @set: Inject
    lateinit var imageDetailsAnimator: ImageDetailsAnimator

    private var _binding: FragmentImageDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { imageDetailsAnimator.postponeEnterTransition(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photo = arguments?.get(PARAM_PHOTO) as? ImageDetailsView
        renderImageDetails(photo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {
        imageDetailsAnimator.fadeInvisible(binding.scrollView, binding.imageDetails)
    }

    private fun renderImageDetails(photo: ImageDetailsView?) {
        photo?.let {
            activity?.let {
                photo.url?.let { it1 ->
                    binding.imagePoster.loadUrlAndPostponeEnterTransition(
                        it1, it
                    )
                }
                (it as BaseActivity).toolbar().title = photo.desc
            }
            with(binding) {
                imageSummary.text = photo.desc
                imageCast.text = photo.desc
                imageDirector.text = photo.desc
                imageYear.text = "year.toString()"
            }
        }
        imageDetailsAnimator.fadeVisible(binding.scrollView, binding.imageDetails)
    }
}

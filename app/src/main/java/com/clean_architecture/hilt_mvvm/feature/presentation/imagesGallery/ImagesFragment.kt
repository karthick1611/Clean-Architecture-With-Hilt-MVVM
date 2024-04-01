package com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.clean_architecture.hilt_mvvm.R
import com.clean_architecture.hilt_mvvm.core.extension.failure
import com.clean_architecture.hilt_mvvm.core.extension.invisible
import com.clean_architecture.hilt_mvvm.core.extension.observe
import com.clean_architecture.hilt_mvvm.core.extension.visible
import com.clean_architecture.hilt_mvvm.core.failure.Failure
import com.clean_architecture.hilt_mvvm.core.platform.BaseFragment
import com.clean_architecture.hilt_mvvm.databinding.FragmentImagesBinding
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import com.clean_architecture.hilt_mvvm.feature.presentation.failure.MovieFailure

class ImagesFragment : BaseFragment() {

//    @set: Inject
//    lateinit var navigator: Navigator

    private var imagesAdapter: ImageAdapter?= null

    private val imagesViewModel: ImagesViewModel by viewModels()

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagesAdapter = ImageAdapter()

        with(imagesViewModel) {
            observe(imageDetails, ::renderImageDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadImagesList()
    }

    private fun loadImagesList() {
        showProgress()
        imagesViewModel.loadImages("Aliens")
    }

    private fun initializeView() {
        binding.photoRecyclerview.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.photoRecyclerview.adapter = imagesAdapter
//        imagesAdapter.clickListener = { movie, navigationExtras ->
//            navigator.showMovieDetails(requireActivity(), movie, navigationExtras)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {

    }

    private fun renderImageDetails(images: ImageResponse?) {
        images?.let { imageList ->
            activity?.let {
                imagesAdapter?.collection = imageList.photos.orEmpty()
                hideProgress()
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        binding.photoRecyclerview.invisible()
        binding.photoRecyclerview.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadImagesList)
    }

}
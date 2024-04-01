package com.clean_architecture.hilt_mvvm.feature.presentation.imagesGallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clean_architecture.hilt_mvvm.core.extension.loadFromUrl
import com.clean_architecture.hilt_mvvm.core.navigation.Navigator
import com.clean_architecture.hilt_mvvm.databinding.GalleryListItemBinding
import com.clean_architecture.hilt_mvvm.feature.domain.model.Photo
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    internal var collection: List<Photo> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (Photo, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageBinding = GalleryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(imageBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position], clickListener)
    }

    override fun getItemCount(): Int = collection.size

    class ViewHolder(private val binding : GalleryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, clickListener: (Photo, Navigator.Extras) -> Unit) {
            photo.src?.large?.let { binding.imageIv.loadFromUrl(it) }
            itemView.setOnClickListener {
                clickListener(
                    photo,
                    Navigator.Extras(binding.layoutImages)
                )
            }
        }
    }
}
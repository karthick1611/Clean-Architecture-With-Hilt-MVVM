package com.clean_architecture.hilt_mvvm.feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageSrc
import com.clean_architecture.hilt_mvvm.feature.domain.model.ListPhotos

@Database(entities = [ImageResponse::class], version = 4, exportSchema = false)
@TypeConverters(ListPhotos::class, ImageSrc::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        const val DATABASE_NAME: String = "photo_db"
    }
}
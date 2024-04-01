package com.clean_architecture.hilt_mvvm.feature.data.network.apiService

import com.clean_architecture.hilt_mvvm.feature.domain.model.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_IMAGES="search"

interface ImagesApiService {

    @GET(GET_IMAGES)
    fun get(@Query("query")  query: String, @Query("per_page")  count: Int): Call<ImageResponse>

}
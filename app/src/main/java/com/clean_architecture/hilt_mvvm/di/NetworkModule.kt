package com.clean_architecture.hilt_mvvm.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.clean_architecture.hilt_mvvm.feature.data.network.apiService.ApiService
import com.clean_architecture.hilt_mvvm.feature.data.network.apiService.ImagesApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL_IMAGES="https://api.pexels.com/v1/"
    private const val BASE_URL="https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/"
//    private const val API_KEY="PASTE YOUR API KEY"
    private const val API_KEY="563492ad6f91700001000001a908a3ee509745d2b2173db3806f76f6"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(provideHttpClient: OkHttpClient, provideGsonBuilder : Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideHttpClient)
            .addConverterFactory(GsonConverterFactory.create(provideGsonBuilder))
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("Images")
    fun provideHttpClientImages(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @Named("Images")
    fun provideRetrofitImages(@Named("Images") provideHttpClientImages: OkHttpClient, provideGsonBuilder : Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_IMAGES)
            .client(provideHttpClientImages)
            .addConverterFactory(GsonConverterFactory.create(provideGsonBuilder))
    }

    @Provides
    @Singleton
    fun provideServiceImages(@Named("Images") retrofit: Retrofit.Builder): ImagesApiService {
        return retrofit
            .build()
            .create(ImagesApiService::class.java)
    }

}
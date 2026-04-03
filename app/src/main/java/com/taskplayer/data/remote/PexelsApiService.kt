package com.taskplayer.data.remote

import com.taskplayer.core.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {

    @GET("search")
    suspend fun searchVideos(
        @Query("query")       query: String,
        @Query("per_page")    perPage: Int = Constants.MAX_RESULTS,
        @Query("orientation") orientation: String = "portrait"
    ): PexelsVideoResponse

    companion object {
        fun create(): PexelsApiService {
            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", Constants.PEXELS_API_KEY)
                    .build()
                chain.proceed(request)
            }

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.PEXELS_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PexelsApiService::class.java)
        }
    }
}
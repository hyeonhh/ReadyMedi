package com.teammeditalk.medicalconnect.data.client

import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.data.service.KakaoSearchService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoClient {
    private const val BASE_URL = "https://dapi.kakao.com/"
    private const val API_KEY = BuildConfig.KakaoApiKey
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    private val okHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val original = it.request()
                val request =
                    original
                        .newBuilder()
                        .header("Authorization", "KakaoAK ${BuildConfig.KakaoApiKey}")
                        .header("content-Type", "application/json")
                        .header("charset", "UTF-8")
                        .build()
                it.proceed(request)
            }.build()

    private val retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val kakaoSearchService: KakaoSearchService = retrofit.create(KakaoSearchService::class.java)
}

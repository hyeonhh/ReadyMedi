package com.teammeditalk.medicalconnect.data.client

import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.data.service.ForeignLanguageService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ForeignLanguageClient {
    // 1. BaseUrl , Apikey 세팅
    private const val BASE_URL = "https://api.odcloud.kr/"
    private const val API_KEY = BuildConfig.MedicalApiKey

    // 2. log를 위한 Interceptor 세팅
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    // 3. okHttpClient 세팅

    private val okHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val original = it.request()
                val request =
                    original
                        .newBuilder()
                        .header("Authorization", "Infuser ${API_KEY}")
                        .header("content-Type", "application/json")
                        .header("charset", "UTF-8")
                        .build()
                it.proceed(request)
            }.build()

    // 4. retrofit 세팅

    private val retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // 5. service 객체 세팅
    val foreignLanguageService: ForeignLanguageService = retrofit.create(ForeignLanguageService::class.java)
}

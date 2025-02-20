package com.teammeditalk.medicalconnect.data.network

import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.data.qualifier.KakaoRetrofit
import com.teammeditalk.medicalconnect.data.qualifier.LocationRetrofit
import com.teammeditalk.medicalconnect.data.service.KakaoSearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {
    // 1. logging interceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    // 2. okhttpclient
    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
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

    // 3. retrofit

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideRetrofit(
        @LocationRetrofit okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://dapi.kakao.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // 4. service
    @Provides
    @Singleton
    fun provideKakaoSearchService(
        @KakaoRetrofit retrofit: Retrofit,
    ): KakaoSearchService = retrofit.create(KakaoSearchService::class.java)
}

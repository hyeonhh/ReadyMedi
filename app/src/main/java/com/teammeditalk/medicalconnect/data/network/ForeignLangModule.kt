package com.teammeditalk.medicalconnect.data.network

import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.data.qualifier.ForeignLangRetrofit
import com.teammeditalk.medicalconnect.data.service.ForeignLanguageService
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
object ForeignLangModule {
    @Provides
    @Singleton
    fun provideApiKey(): String = BuildConfig.MedicalApiKey

    @Provides
    @Singleton
    @ForeignLangRetrofit
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val apiKey = BuildConfig.MedicalApiKey
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val original = it.request()
                val request =
                    original
                        .newBuilder()
                        .header("Authorization", "Infuser $apiKey")
                        .header("content-Type", "application/json")
                        .header("charset", "UTF-8")
                        .build()
                it.proceed(request)
            }.build()
    }

    @Provides
    @Singleton
    @ForeignLangRetrofit
    fun provideRetrofit(
        @ForeignLangRetrofit okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.odcloud.kr/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideForeignLangService(
        @ForeignLangRetrofit retrofit: Retrofit,
    ): ForeignLanguageService = retrofit.create(ForeignLanguageService::class.java)
}

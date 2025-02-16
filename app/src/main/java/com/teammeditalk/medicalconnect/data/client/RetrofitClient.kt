package com.teammeditalk.medicalconnect.data.client

import com.google.android.datatransport.runtime.BuildConfig
import com.teammeditalk.medicalconnect.data.service.LocationService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/"

    // 로깅 인터셉터 생성
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.HEADERS // 개발 환경에서는 모든 정보 로깅
                } else {
                    HttpLoggingInterceptor.Level.NONE // 배포 환경에서는 로깅 비활성화
                }
        }

    private val parser = TikXml.Builder().build()

    private val okHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    private val retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .build()

    val locationService: LocationService = retrofit.create(LocationService::class.java)
}

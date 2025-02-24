
package com.teammeditalk.medicalconnect.data.module

import com.teammeditalk.medicalconnect.data.qualifier.LocationRetrofit
import com.teammeditalk.medicalconnect.data.service.LocationService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideParser(): TikXml = TikXml.Builder().build()

    // 2. okhttpclient
    @Provides
    @Singleton
    @LocationRetrofit
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @LocationRetrofit
    fun provideLocationRetrofit(
        @LocationRetrofit okHttpClient: OkHttpClient,
        parser: TikXml,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://apis.data.go.kr/B552657/HsptlAsembySearchService/")
            .client(okHttpClient)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .build()

    @Provides
    @Singleton
    fun provideMedicalLocationService(
        @LocationRetrofit retrofit: Retrofit,
    ): LocationService = retrofit.create(LocationService::class.java)
}

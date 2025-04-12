package com.teammeditalk.medicalconnect.data.service

import com.teammeditalk.medicalconnect.data.model.foreign.ForeignAvailableResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForeignLanguageService {
    @GET("/api/3077892/v1/uddi:5d98d940-9da2-4abb-a809-944f85be0941")
    suspend fun getForeignLanguageAvailableResponse(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String,
    ): Response<ForeignAvailableResponse>

    // 외국어 가능 의료 기관 강남구
    @GET("/api/15142089/v1/uddi:286fdd43-fbce-4284-b5f6-ddfffa215520")
    suspend fun getGangNamForeignAvailableResponse(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String,
    ): Response<ForeignAvailableResponse>
}

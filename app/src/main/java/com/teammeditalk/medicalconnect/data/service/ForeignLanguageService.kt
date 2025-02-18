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
}

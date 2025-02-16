package com.teammeditalk.medicalconnect.data.service

import com.teammeditalk.medicalconnect.data.model.search.HospitalSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoSearchService {
    @GET("/v2/local/search/category.json")
    suspend fun searchLocationByKeyword(
        @Query("category_group_code") category_group_code: String = "HP8",
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int = 2000,
        @Query("page") page: Int,
        @Query("sort") sort: String = "distance",
    ): Response<HospitalSearchResponse>
}

package com.teammeditalk.medicalconnect.data.service

import com.teammeditalk.medicalconnect.data.model.location.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("/B552657/HsptlAsembySearchService/getHsptlMdcncLcinfoInqire")
    suspend fun getHospitalLocationNearbyMe(
        @Query("serviceKey") serviceKey: String,
        @Query("WGS84_LAT") latitude: Double,
        @Query("WGS84_LON") longitude: Double,
    ): Response<LocationResponse>
}

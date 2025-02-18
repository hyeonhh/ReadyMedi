package com.teammeditalk.medicalconnect.data.model.foreign

import kotlinx.serialization.Serializable

@Serializable
data class ForeignAvailableResponse(
    val currentCount: Int,
    val data: List<Data>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int,
)

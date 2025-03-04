package com.teammeditalk.medicalconnect.data.model.info

data class HealthInfo(
    val diseaseList: List<String> = emptyList(),
    val allergyList: List<String> = emptyList(),
    val familyDiseaseList: List<String> = emptyList(),
    val drugList: List<String> = emptyList(),
    val drugTakingDuration: String? = "",
    val drugTakingCount: Int = 0,
)

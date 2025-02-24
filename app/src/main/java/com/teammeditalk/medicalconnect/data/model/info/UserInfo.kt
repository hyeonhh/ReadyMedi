package com.teammeditalk.medicalconnect.data.model.info

data class HealthInfo(
    val disease: List<String> = emptyList(),
    val allergy: List<String> = emptyList(),
    val family_disease: List<String> = emptyList(),
)

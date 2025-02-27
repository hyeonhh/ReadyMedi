package com.teammeditalk.medicalconnect.data.model.symptom

// 증상 입력
data class SymptomResponse(
    val mainSymptom: String = "",
    val hospitalType: String = "",
    val region: String = "",
    val start: String = "",
    val type: String = "",
    val degree: Float = 0.0f,
    val duration: String = "",
    val releaseList: List<String> = emptyList(),
    val worseList: List<String> = emptyList(),
    val otherSymptom: String = "",
    val additionalComment: String = "",
)

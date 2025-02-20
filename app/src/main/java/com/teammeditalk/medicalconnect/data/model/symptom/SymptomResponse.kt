package com.teammeditalk.medicalconnect.data.model.symptom

// 증상 입력
data class SymptomResponse(
    val region: String,
    val start: String,
    val type: String,
    val degree: Int,
    val duration: String,
    val effect: String,
    val otherSymptom: String,
    val additionalComment: String,
)

package com.teammeditalk.medicalconnect.data.model.foreign

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val 명칭: String,
    val 연번: Int,
    val 외국어: String,
    val 전화번호: String,
    val 종별: String,
    val 주소: String,
    val 진료과목: String,
)

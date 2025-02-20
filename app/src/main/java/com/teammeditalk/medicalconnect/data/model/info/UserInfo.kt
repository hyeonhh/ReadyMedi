package com.teammeditalk.medicalconnect.data.model.info

data class UserInfo(
    val language: String,
    val disease: List<String>,
    val allergy: List<String>,
    val family_disease: List<String>,
)

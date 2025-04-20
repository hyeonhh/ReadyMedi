package com.teammeditalk.medicalconnect.data.model.question

data class WomenQuestionResponse(
    val timeStamp: String = "",
    val hospitalType: String = "",
    val symptomTitle: String = "",
    val symptomContent: String = "",
    val startDate: String = "",
    val type: List<String> = emptyList(),
    val degree: String = "",
    val duration: String = "",
    val otherSymptom: List<String> = emptyList(),
    val worstList: List<String> = emptyList(),
    val additionalInput: String = "",
    val lastDate: String = "",
    val availablePregnancy: String = "",
    val regularity: String = "",
    val additionalInputByKorean: String = "",
)

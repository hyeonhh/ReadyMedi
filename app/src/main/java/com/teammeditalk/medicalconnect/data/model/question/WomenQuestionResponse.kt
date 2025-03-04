package com.teammeditalk.medicalconnect.data.model.question

data class WomenQuestionResponse(
    val timeStamp: String = "",
    val hospitalType: String = "",
    val symptomTitle: String = "",
    val symptomContent: String = "",
    val startDate: String = "",
    val type: String = "",
    val degree: String = "",
    val duration: String = "",
    val otherSymptom: List<String> = emptyList(),
    val additionalInput: String = "",
    val lastDate: String = "",
    val isAvailablePregnancy: String = "",
    val regularity: String = "",
)

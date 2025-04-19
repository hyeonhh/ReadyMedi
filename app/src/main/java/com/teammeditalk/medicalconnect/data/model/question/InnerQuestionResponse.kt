package com.teammeditalk.medicalconnect.data.model.question

data class InnerQuestionResponse(
    val timeStamp: String = "",
    val hospitalType: String = "",
    val symptomTitle: String = "",
    val symptomContent: String = "",
    val region: String = "",
    val startDate: String = "",
    val type: List<String> = emptyList(),
    val degree: String = "",
    val duration: String = "",
    val worseList: List<String> = emptyList(),
    val otherSymptom: List<String> = emptyList(),
    val additionalInput: String = "",
    val additionalInputByKorean: String = "",
)

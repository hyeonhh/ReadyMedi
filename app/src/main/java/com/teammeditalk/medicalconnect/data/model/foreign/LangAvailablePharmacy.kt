package com.teammeditalk.medicalconnect.data.model.foreign

data class LangAvailablePharmacy(
    val phone: String = "",
    val region: String = "",
    val pharmacyName: String = "",
    val address: String = "",
    val availableLanguageList: List<String> = emptyList(),
)

package com.teammeditalk.medicalconnect.data.model.search

// 병원 개별 아이템
data class SearchLocationItem(
    val time: String = "",
    val type: String = "Hospital",
    val id: String? = null,
    val name: String = "",
    val categoryName: String = "",
    val categoryGroupCode: String = "",
    val phone: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val placeUrl: String = "",
    val distance: String = "",
    val availableForeignLanguageList: List<String> = emptyList(),
)

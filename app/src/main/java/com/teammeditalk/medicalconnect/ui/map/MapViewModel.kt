package com.teammeditalk.medicalconnect.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.data.model.foreign.LangAvailablePharmacy
import com.teammeditalk.medicalconnect.data.model.search.SearchLocationItem
import com.teammeditalk.medicalconnect.data.service.ForeignLanguageService
import com.teammeditalk.medicalconnect.data.service.KakaoSearchService
import com.teammeditalk.medicalconnect.data.service.LocationService
import com.teammeditalk.medicalconnect.ui.util.ExcelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val kakaoSearchService: KakaoSearchService,
        private val locationService: LocationService,
        private val foreignLanguageService: ForeignLanguageService,
    ) : ViewModel() {
        private val _langPharmacyList = MutableStateFlow(listOf<LangAvailablePharmacy>())
        val langPharmacyList = _langPharmacyList.asStateFlow()

        private val _hospitalList = MutableStateFlow(listOf<SearchLocationItem>())
        val hospitalList = _hospitalList.asStateFlow()

        private val _pharmacyList = MutableStateFlow(listOf<SearchLocationItem>())
        val pharmacyList = _pharmacyList.asStateFlow()

        init {
            getGangNamForeignAvailableList()
        }

        // 외국어 가능 약국 리스트
        fun loadForeignLanguageAvailablePharmacyList(excelHelper: ExcelHelper) {
            viewModelScope.launch {
                val result = excelHelper.readForeignLanguageList()
                if (result.isNotEmpty()) {
                    _langPharmacyList.value = result
                }
            }
        }

        fun searchHospitalByKeyword(
            latitude: String,
            longitude: String,
            page: Int,
        ) {
            viewModelScope.launch {
                val result =
                    kakaoSearchService.searchLocationByKeyword(
                        authorization = "KakaoAK ${BuildConfig.KakaoApiKey}",
                        category_group_code = "HP8",
                        x = longitude,
                        y = latitude,
                        page = page,
                    )
                if (result.isSuccessful) {
                    val documents = result.body()?.documents
                    val list = mutableListOf<SearchLocationItem>()

                    documents?.map {
                        list.add(
                            SearchLocationItem(
                                type = "Hospital",
                                id = it.id,
                                name = it.place_name,
                                categoryName = it.category_name,
                                phone = it.phone,
                                address = it.address_name,
                                latitude = it.y,
                                longitude = it.x,
                                placeUrl = it.place_url,
                                distance = it.distance,
                            ),
                        )
                    }
                    _hospitalList.value = list
                    Timber.d("success search hospital :${result.body()}")
                } else {
                    Timber.e("failed to search hospital :${result.errorBody()}")
                }
            }
        }

        private fun extractRegion(address: String): String =
            try {
                // 1. 공백으로 분리
                val parts = address.split(" ")
                // 2. 두 번째 요소(index 1)를 가져옴
                parts.getOrNull(1) ?: ""
            } catch (e: Exception) {
                "" // 에러 발생 시 빈 문자열 반환
            }

        fun searchPharmacyLocation(
            longitude: String,
            latitude: String,
            page: Int,
        ) {
            viewModelScope.launch {
                if (_langPharmacyList.value.isNotEmpty()) {
                    val result =
                        kakaoSearchService.searchLocationByKeyword(
                            authorization = "KakaoAK ${BuildConfig.KakaoApiKey}",
                            category_group_code = "PM9",
                            x = longitude,
                            y = latitude,
                            page = page,
                        )
                    if (result.isSuccessful) {
                        val list = mutableListOf<SearchLocationItem>()

                        result.body()?.documents?.map { document ->
                            // 1. 구 지역을 추출
                            val region = extractRegion(document.address_name)
                            // 2. 외국어 가능 약국 명단에서 해당 지역 데이터 비교

                            if (_langPharmacyList.value.isEmpty()) return@launch
                            val filteredList =
                                _langPharmacyList.value.filter {
                                    it.region == region
                                }

                            // 만약 해당 지역 데이터에 현재 약국에 있다면 , 그 약국의 가능한 외국어 리스트 가져오기
                            val filteredAddress =
                                filteredList
                                    .find {
                                        it.address.contains(document.road_address_name)
                                    }?.availableLanguageList ?: emptyList()

                            list.add(
                                SearchLocationItem(
                                    type = "Phar",
                                    id = document.id,
                                    name = document.place_name,
                                    categoryName = document.category_name,
                                    phone = document.phone,
                                    address = document.road_address_name,
                                    latitude = document.y,
                                    longitude = document.x,
                                    placeUrl = document.place_url,
                                    distance = document.distance,
                                    availableForeignLanguageList = filteredAddress,
                                ),
                            )
                        }
                        _pharmacyList.value = list
                    } else {
                        Timber.e("failed to get pharmacy list : ${result.errorBody()}")
                    }
                }
            }
        }

        fun getLocation(
            latitude: Double,
            longitude: Double,
        ) {
            viewModelScope.launch {
                val apiKey = BuildConfig.MedicalApiKey
                val result =
                    locationService.getHospitalLocationNearbyMe(
                        serviceKey = apiKey,
                        latitude = latitude,
                        longitude = longitude,
                    )

                if (result.isSuccessful) {
                    Timber.d("success :${result.body()?.body?.items}")
                } else {
                    Timber.d("failed to get location :${result.errorBody()}")
                }
            }
        }

        fun getForeignLanguageAvailableList() {
            viewModelScope.launch {
                val apiKey = BuildConfig.MedicalApiKey
                val result =
                    foreignLanguageService.getForeignLanguageAvailableResponse(
                        serviceKey = apiKey,
                        page = 1,
                        perPage = 10,
                    )
                if (result.isSuccessful) {
                    Timber.d("success :${result.body()?.data}")
                } else {
                    Timber.d("failed to get foreignLanguage response :${result.errorBody()}")
                }
            }
        }

        // 강남구 외국어 가능 의료 기관
        private fun getGangNamForeignAvailableList() {
            viewModelScope.launch {
                val apiKey = BuildConfig.MedicalApiKey
                val result =
                    foreignLanguageService.getGangNamForeignAvailableResponse(
                        serviceKey = apiKey,
                        page = 1,
                        perPage = 10,
                    )
                if (result.isSuccessful) {
                    Timber.d("gang nam success :${result.body()?.data}")
                } else {
                    Timber.d("failed to get foreignLanguage response :${result.errorBody()}")
                }
            }
        }
    }

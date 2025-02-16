package com.teammeditalk.medicalconnect.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.data.client.KakaoClient
import com.teammeditalk.medicalconnect.data.client.RetrofitClient
import com.teammeditalk.medicalconnect.data.model.search.SearchLocationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MapViewModel : ViewModel() {
    private val _hospitalList = MutableStateFlow(listOf<SearchLocationItem>())
    val hospitalList = _hospitalList.asStateFlow()

    private val _pharmacyList = MutableStateFlow(listOf<SearchLocationItem>())
    val pharmacyList = _pharmacyList.asStateFlow()

    fun searchHospitalByKeyword(
        latitude: String,
        longitude: String,
        page: Int,
    ) {
        viewModelScope.launch {
            val result =
                KakaoClient.kakaoSearchService.searchLocationByKeyword(
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
                Timber.d("_hospitalList :${_hospitalList.value}")

                Timber.d("success search hospital :${result.body()}")
            } else {
                Timber.e("failed to search hospital :${result.errorBody()}")
            }
        }
    }

    fun searchPharmacyLocation(
        longitude: String,
        latitude: String,
        page: Int,
    ) {
        viewModelScope.launch {
            val result =
                KakaoClient.kakaoSearchService.searchLocationByKeyword(
                    category_group_code = "PM9",
                    x = longitude,
                    y = latitude,
                    page = page,
                )
            if (result.isSuccessful) {
                val list = mutableListOf<SearchLocationItem>()
                result.body()?.documents?.map {
                    list.add(
                        SearchLocationItem(
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
                _pharmacyList.value = list
            } else {
                Timber.e("failed to get pharmacy list : ${result.errorBody()}")
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
                RetrofitClient.locationService.getHospitalLocationNearbyMe(
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
}

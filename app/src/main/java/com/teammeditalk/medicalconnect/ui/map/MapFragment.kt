package com.teammeditalk.medicalconnect.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.data.model.search.SearchLocationItem
import com.teammeditalk.medicalconnect.databinding.FragmentMapBinding
import com.teammeditalk.medicalconnect.ui.ExcelHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class MapFragment :
    BaseFragment<FragmentMapBinding>(
        FragmentMapBinding::inflate,
    ) {
    private var kakaoMap: KakaoMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0
    private val excelHelper by lazy { ExcelHelper(requireContext()) }

    private val viewModel: MapViewModel by viewModels()

    // 요청할 위치 권한 목록입니다.
    private val locationPermissions =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

    // 권한 확인
    private fun checkLocationPermission() {
        if (checkSelfPermission(
                requireContext(),
                locationPermissions[0],
            ) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(
                requireContext(),
                locationPermissions[1],
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("권한 있음")
        } else {
            Timber.d("권한 없음")
            ActivityCompat.requestPermissions(
                requireActivity(),
                locationPermissions,
                1001,
            )
        }
    }

    // 권한 요청 다이얼로그에 응답하면 이 메소드가 실행된다.
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            Timber.d("권한 있음")
        } else {
            Timber.d("권한 없음")
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // 여기를 바인딩으로 안바꿔줘서 라이브러리가 적용 안된 거였다!!
        checkLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    // 지도 api가 정상적으로 종료될 때 호출
                }

                override fun onMapError(e: Exception?) {
                    // 인증 실패 및 지도 사용 중 에러 발생
                    e?.printStackTrace()
                    Timber.d("지도 에러 :${e?.message}")
                }
            },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(map: KakaoMap) {
                    kakaoMap = map
                    getCurrentLocation()
                    onLabelClickListener()
                }
            },
        )
    }

    // https://apis.map.kakao.com/android_v2/reference/com/kakao/vectormap/KakaoMap.OnLabelClickListener.html
    private fun onLabelClickListener() {
        kakaoMap?.setOnLabelClickListener { kakaoMap, labelLayer, label ->
            val bottomSheet = MapInfoBottomSheet(label.tag as SearchLocationItem)
            bottomSheet.show(parentFragmentManager, "dialog")
            true
        }
    }

    override fun onStart() {
        super.onStart()
        getHospitalNearByMe()
        getPharmacyNearByMe()
        viewModel.getForeignLanguageAvailableList()
        viewModel.loadForeignLanguageAvailablePharmacyList(excelHelper)
    }

    private fun getCurrentLocation() {
        if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lifecycleScope.launch {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    it?.let {
                        val currentLatLng = LatLng.from(it.latitude, it.longitude)
                        latitude = it.latitude
                        longitude = it.longitude
                        viewModel.getLocation(currentLatLng.latitude, currentLatLng.longitude)
                        viewModel.searchHospitalByKeyword(currentLatLng.latitude.toString(), currentLatLng.longitude.toString(), 1)
                        // viewModel.searchPharmacyLocation(currentLatLng.longitude.toString(), currentLatLng.latitude.toString(), 1)
                        lifecycleScope.launch {
                            viewModel.langPharmacyList.collect {
                                if (it.isNotEmpty()) {
                                    viewModel.searchPharmacyLocation(
                                        currentLatLng.longitude.toString(),
                                        currentLatLng.latitude.toString(),
                                        1,
                                    )
                                }
                            }
                        }

                        kakaoMap?.let { map ->

                            // 카메라 이동

                            map.moveCamera(CameraUpdateFactory.newCenterPosition(currentLatLng, 20))

                            // 1. LabelStyles 생성하기
                            val style =
                                LabelStyles.from(
                                    LabelStyle.from(R.drawable.location),
                                )

                            val markerStyles =
                                map.labelManager?.addLabelStyles(
                                    style,
                                )

                            // LabelOptions 생성하기
                            val options =
                                LabelOptions
                                    .from(LatLng.from(it.latitude, it.longitude))
                                    .setStyles(markerStyles)

                            Timber.d("latitude :${it.latitude}, longitude:${it.longitude}")
                            val layer = kakaoMap?.labelManager?.layer

                            // LabelLayer 가져오기
                            val label = layer?.addLabel(options)
                        }
                    }
                }
            }
        }
    }

    private fun putLabelOnLocation(
        iconResId: Int,
        list: List<SearchLocationItem>,
    ) {
        kakaoMap.let { map ->
            // 1. LabelStyles 생성하기
            val style =
                LabelStyles.from(
                    LabelStyle.from(iconResId),
                )

            val markerStyles =
                map?.labelManager?.addLabelStyles(
                    style,
                )

            list.forEach {
                val position =
                    LatLng.from(
                        it.latitude.toDouble(),
                        it.longitude.toDouble(),
                    )

                // LabelOptions 생성하기
                val options =
                    LabelOptions
                        .from(position)
                        .setStyles(markerStyles)

                val layer = kakaoMap?.labelManager?.layer
                val label = layer?.addLabel(options)
                label?.tag = it
                map?.moveCamera(
                    CameraUpdateFactory.newCenterPosition(
                        LatLng.from(
                            latitude,
                            longitude,
                        ),
                        15,
                    ),
                )
            }
        }
    }

    private fun getHospitalNearByMe() {
        lifecycleScope.launch {
            viewModel.hospitalList.collectLatest {
                if (it.isNotEmpty()) {
                    putLabelOnLocation(R.drawable.location_blue, it)
                } else {
                    return@collectLatest
                }
            }
        }
    }

    private fun getPharmacyNearByMe() {
        lifecycleScope.launch {
            viewModel.pharmacyList.collectLatest {
                if (it.isNotEmpty()) {
                    putLabelOnLocation(R.drawable.location_green, it)
                }
            }
        }
    }
}

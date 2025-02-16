package com.teammeditalk.medicalconnect.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.data.model.search.SearchLocationItem
import com.teammeditalk.medicalconnect.databinding.ActivityMapBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class MapActivity : AppCompatActivity() {
    private lateinit var startPosition: LatLng
    private lateinit var centerLabel: Label
    private var kakaoMap: KakaoMap? = null
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0

    private val viewModel: MapViewModel by viewModels()

    // 요청할 위치 권한 목록입니다.
    private val locationPermissions =
        arrayOf<String>(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

    // 권한 확인
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                locationPermissions[0],
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                locationPermissions[1],
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("권한 있음")
        } else {
            ActivityCompat.requestPermissions(
                this,
                locationPermissions,
                1001,
            )
        }
    }

    // 권한 요청 다이얼로그에 응답하면 이 메소드가 실행된다.
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        // 여기를 바인딩으로 안바꿔줘서 라이브러리가 적용 안된 거였다!!
        setContentView(binding.root)
        checkLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.mapView.start(
            object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                    // 지도 api가 정상적으로 종료될 때 호출
                }

                override fun onMapError(e: Exception?) {
                    // 인증 실패 및 지도 사용 중 에러 발생
                    e?.printStackTrace()
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
            Timber.d("labelLayer $labelLayer , label :${label.tag}")
            val bottomSheet = MapInfoBottomSheet(label.tag as SearchLocationItem)
            bottomSheet.show(supportFragmentManager, "dialog")
            true
        }
    }

    override fun onStart() {
        super.onStart()
        getHospitalNearByMe()
        getPharmacyNearByMe()
    }

    private fun getCurrentLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lifecycleScope.launch {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    it?.let {
                        val currentLatLng = LatLng.from(it.latitude, it.longitude)
                        latitude = it.latitude
                        longitude = it.longitude
                        viewModel.getLocation(currentLatLng.latitude, currentLatLng.longitude)
                        viewModel.searchHospitalByKeyword(currentLatLng.latitude.toString(), currentLatLng.longitude.toString(), 1)
                        viewModel.searchPharmacyLocation(currentLatLng.longitude.toString(), currentLatLng.latitude.toString(), 1)
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

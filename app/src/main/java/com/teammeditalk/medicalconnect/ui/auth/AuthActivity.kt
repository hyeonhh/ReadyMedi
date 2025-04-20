package com.teammeditalk.medicalconnect.ui.auth

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityAuthBinding
import com.teammeditalk.medicalconnect.ui.auth.onboarging.FirstOnboardingFragment
import com.teammeditalk.medicalconnect.ui.auth.onboarging.SecondOnboardingFragment
import com.teammeditalk.medicalconnect.ui.auth.onboarging.ThirdOnBoardingFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity :
    BaseActivity<ActivityAuthBinding>(
        ActivityAuthBinding::inflate,
    ) {
    private var fragmentNum = 0
    private var touchPoint = 0f

    override fun onBindLayout() {
        super.onBindLayout()

        binding.authLayout.setOnTouchListener(
            object : OnTouchListener {
                override fun onTouch(
                    v: View?,
                    event: MotionEvent?,
                ): Boolean {
                    if (event != null) {
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                if (event != null) {
                                    touchPoint = event.getX()
                                }
                            }

                            MotionEvent.ACTION_UP -> {
                                if (event != null) {
                                    touchPoint = touchPoint - event.getX()
                                    if (Math.abs(touchPoint) < 100) return false
                                    if (touchPoint > 0) {
                                        // 손가락을 우에서 좌로 움직였을 때 오른쪽 화면 생성
                                        Timber.d("손가락을 우에서 좌로 이동")
                                        if (fragmentNum == 0) {
                                            supportFragmentManager
                                                .beginTransaction()
                                                .replace(
                                                    R.id.fragment_container_view,
                                                    SecondOnboardingFragment(),
                                                ).commit()
                                            fragmentNum = 1
                                        } else if (fragmentNum == 1) {
                                            supportFragmentManager
                                                .beginTransaction()
                                                .replace(
                                                    R.id.fragment_container_view,
                                                    ThirdOnBoardingFragment(),
                                                ).commit()
                                            fragmentNum = 2
                                        }
                                    } else {
                                        Timber.d("손가락을 좌에서 우로 이동")
                                        if (fragmentNum == 2) {
                                            supportFragmentManager
                                                .beginTransaction()
                                                .replace(
                                                    R.id.fragment_container_view,
                                                    SecondOnboardingFragment(),
                                                ).commit()
                                            fragmentNum = 1
                                        } else if (fragmentNum == 1) {
                                            supportFragmentManager
                                                .beginTransaction()
                                                .replace(
                                                    R.id.fragment_container_view,
                                                    FirstOnboardingFragment(),
                                                ).commit()
                                            fragmentNum = 0
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return true
                }
            },
        )
    }
}

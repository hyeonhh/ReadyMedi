package com.teammeditalk.medicalconnect.ui.auth

import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity :
    BaseActivity<ActivityAuthBinding>(
        ActivityAuthBinding::inflate,
    ) {
    private var fragmentNum = 0

    override fun onBindLayout() {
        super.onBindLayout()

        // gestureDetector = GestureDetector(this, this)

//        binding.authLayout.setOnTouchListener { _, event ->
//            gestureDetector.onTouchEvent(event)
//            true
//        }

//        lifecycleScope.launch {
//            viewModel.uid.collectLatest {
//                if (it != "") {
//                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//        }
    }

//
//
//    // 다른 필수 메서드들 구현 (간략화를 위해 생략)
//    override fun onDown(e: MotionEvent): Boolean = true
//
//    override fun onShowPress(e: MotionEvent) {}
//
//    override fun onSingleTapUp(e: MotionEvent): Boolean = false
//
//    override fun onScroll(
//        e1: MotionEvent?,
//        e2: MotionEvent,
//        distanceX: Float,
//        distanceY: Float,
//    ): Boolean = false
//
//    override fun onLongPress(e: MotionEvent) {}
//
//    // GestureDetector.OnGestureListener 구현
//    override fun onFling(
//        e1: MotionEvent?,
//        e2: MotionEvent,
//        velocityX: Float,
//        velocityY: Float,
//    ): Boolean {
//        // e1: 시작 포인트, e2: 끝 포인트
//        try {
//            val diffX = e2.x - e1!!.x
//            val diffY = e2.y - e1.y
//
//            Timber.d("x 차이 :$diffX")
//            Timber.d("y 차이 :$diffY")
//
//            if (Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > 50 && Math.abs(velocityX) > 100) {
//                if (diffX < 0) {
//                    when (fragmentNum) {
//                        0 -> {
//                            binding.tvTitle.text = "낯선 한국 병원과 약국\n당신의 의료 가이드 Ready Medi"
//                            binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding_group1))
//                            binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator1))
//                            fragmentNum = 1
//                        }
//                        1 -> {
//                            binding.tvTitle.text = "증상을 미리 입력하면\n어려운 병원과 약국도 걱정없어요"
//                            binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding_group2))
//                            binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator2))
//                            fragmentNum = 2
//                        }
//                        2 -> {
//                            binding.tvTitle.text = "외국어 가능 의료기관부터\n외국인 보험 정보까지 쏙쏙"
//                            binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding3))
//                            binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator3))
//                            fragmentNum = -1
//                        }
//                    }
//                    // 오른쪽에서 왼쪽으로 스와이프
//                    Timber.d("스와이프")
//                }
//            } else {
//                when (fragmentNum) {
//                    0 -> {
//                        binding.tvTitle.text = "낯선 한국 병원과 약국\n당신의 의료 가이드 Ready Medi"
//                        binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding_group1))
//                        binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator1))
//                        fragmentNum = -1
//                    }
//                    1 -> {
//                        binding.tvTitle.text = "증상을 미리 입력하면\n어려운 병원과 약국도 걱정없어요"
//                        binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding_group2))
//                        binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator2))
//                        fragmentNum = 0
//                    }
//                    2 -> {
//                        binding.tvTitle.text = "외국어 가능 의료기관부터\n외국인 보험 정보까지 쏙쏙"
//                        binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding3))
//                        binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator3))
//                        fragmentNum = 1
//                    }
//                }
//            }
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//        }
//        return true
//    }
}

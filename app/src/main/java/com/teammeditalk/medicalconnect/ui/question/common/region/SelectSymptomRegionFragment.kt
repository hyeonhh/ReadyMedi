package com.teammeditalk.medicalconnect.ui.question.common.region

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomRegionBinding
import dagger.hilt.android.AndroidEntryPoint

// 1. 증상 부위
@AndroidEntryPoint
class SelectSymptomRegionFragment :
    BaseFragment<FragmentSelectSymptomRegionBinding>(
        FragmentSelectSymptomRegionBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    // todo : 공유 뷰모델 사용하기
    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectSymptomStartFragment)
        }
    }
}

package com.teammeditalk.medicalconnect.ui.home

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentHomeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(
        FragmentHomeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.ivUserProfile.setOnClickListener {
            // 사용자 페이지로 이동
            navController.navigate(R.id.userFragment)
        }

        binding.layoutHistory.userHealthInfo.layout.setOnClickListener {
            navController.navigate(R.id.myHealthInfoFragment)
        }

        binding.layoutHistory.userSymptomHistory.layout.setOnClickListener {
            navController.navigate(R.id.mySymptomHistoryFragment)
        }
        binding.layoutHomeSymptom.materialButton.setOnClickListener {
            val intent = Intent(context, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}

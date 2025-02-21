package com.teammeditalk.medicalconnect.ui.question.common.start

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomStartBinding
import dagger.hilt.android.AndroidEntryPoint

// 2. 언제부터 증상이 발생했는지
@AndroidEntryPoint
class SelectSymptomStartFragment :
    BaseFragment<FragmentSelectSymptomStartBinding>(
        FragmentSelectSymptomStartBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectPainTypeFragment)
        }
    }
}

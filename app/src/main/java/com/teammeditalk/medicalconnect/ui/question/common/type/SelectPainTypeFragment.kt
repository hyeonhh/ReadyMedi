package com.teammeditalk.medicalconnect.ui.question.common.type

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectPainTypeBinding
import dagger.hilt.android.AndroidEntryPoint

// 3. 통증의 성격
@AndroidEntryPoint
class SelectPainTypeFragment :
    BaseFragment<FragmentSelectPainTypeBinding>(
        FragmentSelectPainTypeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.selectPainDegreeFragment)
        }
    }
}

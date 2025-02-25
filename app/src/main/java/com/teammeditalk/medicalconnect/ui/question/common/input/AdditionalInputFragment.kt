package com.teammeditalk.medicalconnect.ui.question.common.input

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAdditionalInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdditionalInputFragment :
    BaseFragment<FragmentAdditionalInputBinding>(
        FragmentAdditionalInputBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()
        binding.btnComplete.setOnClickListener {
            navController.navigate(R.id.symptomResultFragment)
        }
    }
}

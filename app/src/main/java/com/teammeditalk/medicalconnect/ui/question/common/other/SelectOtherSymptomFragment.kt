package com.teammeditalk.medicalconnect.ui.question.common.other

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectOtherSymptomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectOtherSymptomFragment :
    BaseFragment<FragmentSelectOtherSymptomBinding>(
        FragmentSelectOtherSymptomBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.additionalInputFragment)
        }
    }
}

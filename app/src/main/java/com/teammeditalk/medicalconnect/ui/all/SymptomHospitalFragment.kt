package com.teammeditalk.medicalconnect.ui.all

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSymptomHospitalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SymptomHospitalFragment :
    BaseFragment<FragmentSymptomHospitalBinding>(
        FragmentSymptomHospitalBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }
}

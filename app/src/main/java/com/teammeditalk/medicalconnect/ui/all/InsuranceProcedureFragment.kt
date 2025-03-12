package com.teammeditalk.medicalconnect.ui.all

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInsuranceProcedureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsuranceProcedureFragment :
    BaseFragment<FragmentInsuranceProcedureBinding>(
        FragmentInsuranceProcedureBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

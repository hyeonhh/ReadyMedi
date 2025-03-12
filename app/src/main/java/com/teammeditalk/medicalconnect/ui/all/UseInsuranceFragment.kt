package com.teammeditalk.medicalconnect.ui.all

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentUseInsuranceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UseInsuranceFragment :
    BaseFragment<FragmentUseInsuranceBinding>(
        FragmentUseInsuranceBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

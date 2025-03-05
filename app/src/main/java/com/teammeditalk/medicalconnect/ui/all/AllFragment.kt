package com.teammeditalk.medicalconnect.ui.all

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAllBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFragment :
    BaseFragment<FragmentAllBinding>(
        FragmentAllBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.tvSymptomHospital.setOnClickListener {
            navController.navigate(R.id.symptomHospitalFragment)
        }
        binding.tvFreeHospital.setOnClickListener {
            navController.navigate(R.id.freeHospitalFragment2)
        }

        binding.tvFreeCash.setOnClickListener {
            navController.navigate(R.id.freeHospitalPolicyFragment)
        }
    }
}

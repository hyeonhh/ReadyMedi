package com.teammeditalk.medicalconnect.ui.onboarding.drug

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDrugBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDrugFragment :
    BaseFragment<FragmentSelectDrugBinding>(
        FragmentSelectDrugBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.allergySelectFragment)
        }
    }
}

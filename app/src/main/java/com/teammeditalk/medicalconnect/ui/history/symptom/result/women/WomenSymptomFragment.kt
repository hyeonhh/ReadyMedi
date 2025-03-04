package com.teammeditalk.medicalconnect.ui.history.symptom.result.women

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentWomenSymptomResultBinding
import com.teammeditalk.medicalconnect.ui.history.symptom.result.dental.WomenSymptomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WomenSymptomFragment :
    BaseFragment<FragmentWomenSymptomResultBinding>(
        FragmentWomenSymptomResultBinding::inflate,
    ) {
    private val viewModel: WomenSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }

        lifecycleScope.launch {
            viewModel.womenResponse.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomTitle.text = it.symptomTitle
                binding.layoutCurrentSymptom.tvSymptomContent.text = it.symptomContent
                binding.layoutCurrentSymptom.tvSymptomDegree.text = it.degree
                binding.layoutCurrentSymptom.tvSymptomStartDate.text = it.startDate
                //  binding.layoutCurrentSymptom.tvOtherSymptom.text = it.otherSymptom.toString()
                //  binding.layoutCurrentSymptom.tvSymptomWorseList.text = it..toString()
                binding.layoutCurrentSymptom.tvSymptomType.text = it.type
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
                binding.layoutCurrentSymptom.tvPregnancy.text = it.isAvailablePregnancy
                binding.layoutCurrentSymptom.tvReguarlity.text = it.regularity
                binding.layoutCurrentSymptom.tvLastPeriod.text = it.lastDate
            }
        }
    }
}

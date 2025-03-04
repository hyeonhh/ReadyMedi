package com.teammeditalk.medicalconnect.ui.history.symptom.result.genral

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentGeneralSymptomResultBinding
import com.teammeditalk.medicalconnect.ui.history.symptom.result.dental.GeneralSymptomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneralSymptomFragment :
    BaseFragment<FragmentGeneralSymptomResultBinding>(
        FragmentGeneralSymptomResultBinding::inflate,
    ) {
    private val viewModel: GeneralSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }

        lifecycleScope.launch {
            viewModel.generalResponse.collectLatest {
                binding.layoutCurrentSymptom.tvSymptomTitle.text = it.symptomTitle
                binding.layoutCurrentSymptom.tvSymptomContent.text = it.symptomContent
                binding.layoutCurrentSymptom.tvSymptomDegree.text = it.degree
                binding.layoutCurrentSymptom.tvSymptomStartDate.text = it.startDate
                binding.layoutCurrentSymptom.tvOtherSymptom.text = it.otherSymptom.toString()
                binding.layoutCurrentSymptom.tvSymptomWorseList.text = it.worseList.toString()
                binding.layoutCurrentSymptom.tvSymptomType.text = it.type
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
            }
        }
    }
}

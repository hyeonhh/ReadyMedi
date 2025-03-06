package com.teammeditalk.medicalconnect.ui.history.symptom.result.inner

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentHistoryInnerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InnerSymptomFragment :
    BaseFragment<FragmentHistoryInnerBinding>(
        FragmentHistoryInnerBinding::inflate,
    ) {
    private val viewModel: InnerSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.layoutCurrentSymptom.viewModel = viewModel
        binding.layoutCurrentSymptom.lifecycleOwner = viewLifecycleOwner

        binding.layoutUserHealthInfo.viewModel = viewModel
        binding.layoutUserHealthInfo.lifecycleOwner = viewLifecycleOwner

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }

        lifecycleScope.launch {
            viewModel.innerResponse.collectLatest {
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

package com.teammeditalk.medicalconnect.ui.history.symptom.result.dental

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.LayoutCommonQuestionResultBinding
import com.teammeditalk.medicalconnect.databinding.LayoutDentalCurrentSymptomBinding
import com.teammeditalk.medicalconnect.databinding.LayoutDentalSideEffectInputBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DentalSymptomFragment :
    BaseFragment<LayoutCommonQuestionResultBinding>(
        LayoutCommonQuestionResultBinding::inflate,
    ) {
    private val viewModel: DentalSymptomViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }

        // 자식 프래그먼트의 콘텐츠 레이아웃 inflate
        val contentContainer = binding.layoutFrame
        val contentContainer2 = binding.layoutFrame2
        val inflater = LayoutInflater.from(requireContext())

        val currentSymptomBinding = LayoutDentalCurrentSymptomBinding.inflate(inflater, contentContainer, false)
        val sideEffectBindingToUser = LayoutDentalSideEffectInputBinding.inflate(inflater, contentContainer2, false)

        contentContainer.addView(currentSymptomBinding.root)
        contentContainer2.addView(sideEffectBindingToUser.root)

        lifecycleScope.launch {
            viewModel.dentalResponse.collectLatest {
                currentSymptomBinding.tvSymptomTitle.text = it.symptomTitle
                currentSymptomBinding.tvSymptomContent.text = it.symptomContent
                currentSymptomBinding.tvSymptomDegree.text = it.degree
                currentSymptomBinding.tvSymptomStartDate.text = it.startDate
                currentSymptomBinding.tvOtherSymptom.text = it.otherSymptom.toString()
                currentSymptomBinding.tvSymptomWorseList.text = it.worseList.toString()
                currentSymptomBinding.tvSymptomType.text = it.type
                currentSymptomBinding.tvSymptomAnesHistory.text = it.anesHistory.toString()
                sideEffectBindingToUser.tvSideEffect.text = it.sideEffect
                binding.layoutAdditionalInput.tvInput.text = it.additionalInput
            }
        }
    }
}

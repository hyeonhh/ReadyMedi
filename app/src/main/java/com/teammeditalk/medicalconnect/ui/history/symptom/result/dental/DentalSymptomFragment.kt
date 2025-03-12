package com.teammeditalk.medicalconnect.ui.history.symptom.result.dental

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val args by navArgs<DentalSymptomFragmentArgs>()

    override fun onBindLayout() {
        super.onBindLayout()

        viewModel.getSymptom(timeStamp = args.timeStamp, uid = args.uid)

        lifecycleScope.launch {
            viewModel.userHealthInfo.collectLatest {
                with(binding.layoutUserHealthInfo) {
                    tvDisease.text = if (it.diseaseList.isEmpty()) "해당 없음" else it.diseaseList.joinToString(",")
                    tvFamilyDisease.text = if (it.familyDiseaseList.isEmpty()) "해당 없음" else it.familyDiseaseList.joinToString(",")
                    tvDrug.text = if (it.drugList.isEmpty()) "해당 없음" else it.drugList.joinToString(",")
                    tvAllergy.text = if (it.allergyList.isEmpty()) "해당 없음" else it.allergyList.joinToString(",")
                }
            }
        }

        binding.btnBack.visibility = View.GONE
        binding.btnClose.setOnClickListener { navController.popBackStack() }

        // 자식 프래그먼트의 콘텐츠 레이아웃 inflate
        val contentContainer = binding.layoutFrame
        val contentContainer2 = binding.layoutFrame2
        val inflater = LayoutInflater.from(requireContext())

        val currentSymptomBinding = LayoutDentalCurrentSymptomBinding.inflate(inflater, contentContainer, false)
        val sideEffectBindingToUser = LayoutDentalSideEffectInputBinding.inflate(inflater, contentContainer2, false)

        currentSymptomBinding.dentalVM = viewModel
        currentSymptomBinding.useDentalVM = true
        currentSymptomBinding.lifecycleOwner = viewLifecycleOwner

        contentContainer.addView(currentSymptomBinding.root)
        contentContainer2.addView(sideEffectBindingToUser.root)
    }
}

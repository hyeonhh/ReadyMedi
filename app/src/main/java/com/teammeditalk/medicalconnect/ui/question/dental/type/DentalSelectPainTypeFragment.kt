package com.teammeditalk.medicalconnect.ui.question.dental.fragment.type

import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDentalPainTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DentalSelectPainTypeFragment :
    BaseFragment<FragmentSelectDentalPainTypeBinding>(
        FragmentSelectDentalPainTypeBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private val selectedTypeList = mutableListOf<String>()
    private val typeByKorean = mutableListOf<String>()
    private val typeIdList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding.layoutConstraint) {
            for (child in this.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    (child as SelectBox).updateSelected(it.isSelected)
                    if (it.isSelected) {
                        selectedTypeList.add((it as SelectBox).getContent())
                        typeByKorean.add(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
                        typeIdList.add(it.tag.toString())
                    } else {
                        selectedTypeList.remove((it as SelectBox).getContent())
                        typeByKorean.remove(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
                        typeIdList.remove(it.tag.toString())
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }

        binding.btnNext.setOnClickListener {
            if (selectedTypeList.isEmpty()) {
                binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
            } else {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                viewModel.setSymptomType(selectedTypeList)
                viewModel.setTypeId(typeIdList)
                val action = DentalSelectPainTypeFragmentDirections.actionDentalSelectPainTypeFragmentToSelectPainDegreeFragment("치과")
                navController.navigate(action)
            }
        }
    }
}

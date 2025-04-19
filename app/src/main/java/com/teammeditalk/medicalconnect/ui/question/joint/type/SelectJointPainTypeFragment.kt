package com.teammeditalk.medicalconnect.ui.question.common.type

import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectPainTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

// 3. 통증의 성격
@AndroidEntryPoint
class SelectJointPainTypeFragment :
    BaseFragment<FragmentSelectPainTypeBinding>(
        FragmentSelectPainTypeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val selectedTypeList = mutableListOf<String>()
    private val viewModel: QuestionViewModel by activityViewModels()

    private val painTypeByKorean = mutableListOf<String>()
    private val typeIdList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding.layoutConstraint) {
            for (child in this.children) {
                // todo : 중복 선택을 허용해야할까??
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    (child as SelectBox).updateSelected(it.isSelected)
                    if (it.isSelected) {
                        selectedTypeList.add((it as SelectBox).getContent())
                        painTypeByKorean.add(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
                        typeIdList.add(it.tag.toString())
                    } else {
                        selectedTypeList.remove((it as SelectBox).getContent())
                        painTypeByKorean.remove(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
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
                viewModel.setPainTypeByKorean(painTypeByKorean)
                viewModel.setTypeId(typeIdList)
                val action = SelectJointPainTypeFragmentDirections.actionSelectJointPainTypeFragmentToSelectPainDegreeFragment("정형")
                navController.navigate(action)
            }
        }
    }
}

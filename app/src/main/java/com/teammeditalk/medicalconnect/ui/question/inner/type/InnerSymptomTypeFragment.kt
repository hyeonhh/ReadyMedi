package com.teammeditalk.medicalconnect.ui.question.inner.type

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.protobuf.type
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInnerSymptomTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InnerSymptomTypeFragment :
    BaseFragment<FragmentInnerSymptomTypeBinding>(
        FragmentInnerSymptomTypeBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val selectedTypeList = mutableListOf<String>()
    private val navController by lazy { findNavController() }
    private var typeByKorean = mutableListOf<String>()
    private val typeList = mutableListOf<String>()

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
                        typeByKorean.add(it.getKoreanString(it.tag.toString()))
                        typeList.add(it.tag.toString())
                    } else {
                        selectedTypeList.remove((it as SelectBox).getContent())
                        typeByKorean.remove(it.getKoreanString(it.tag.toString()))
                        typeList.remove(it.tag.toString())
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            viewModel.setPainTypeByKorean(typeByKorean)

            viewModel.setTypeId(typeList)
            viewModel.setSymptomType(selectedTypeList)
            navController.navigate(R.id.selectInnerPainDegreeFragment)
        }
    }
}

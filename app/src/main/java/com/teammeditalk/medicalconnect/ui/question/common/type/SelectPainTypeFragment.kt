package com.teammeditalk.medicalconnect.ui.question.common.type

import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectPainTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

// 3. 통증의 성격
@AndroidEntryPoint
class SelectPainTypeFragment :
    BaseFragment<FragmentSelectPainTypeBinding>(
        FragmentSelectPainTypeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val selectedTypeList = mutableListOf<String>()
    private val viewModel: QuestionViewModel by activityViewModels()

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
                    } else {
                        selectedTypeList.remove((it as SelectBox).getContent())
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }

        binding.btnNext.setOnClickListener {
            viewModel.setSymptomType(selectedTypeList)
            navController.navigate(R.id.selectPainDegreeFragment)
        }
    }
}

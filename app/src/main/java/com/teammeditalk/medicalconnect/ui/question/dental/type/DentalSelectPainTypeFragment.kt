package com.teammeditalk.medicalconnect.ui.question.dental.fragment.type

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDentalPainTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
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
            val action = DentalSelectPainTypeFragmentDirections.actionDentalSelectPainTypeFragmentToSelectPainDegreeFragment("치과")
            navController.navigate(action)
        }
    }
}

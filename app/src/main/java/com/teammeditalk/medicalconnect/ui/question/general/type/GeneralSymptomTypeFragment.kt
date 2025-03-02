package com.teammeditalk.medicalconnect.ui.question.inner.type

import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentGeneralSymptomTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralSymptomTypeFragment :
    BaseFragment<FragmentGeneralSymptomTypeBinding>(
        FragmentGeneralSymptomTypeBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private val selectedTypeList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            with(layoutConstraint) {
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
            btnBack.setOnClickListener {
                navController.popBackStack()
            }
            btnNext.setOnClickListener {
                viewModel.setSymptomType(selectedTypeList)
                val bundle = bundleOf("hospital_type" to "일반")
                navController.navigate(R.id.selectGeneralPainDegreeFragment, bundle)
            }
        }
    }
}

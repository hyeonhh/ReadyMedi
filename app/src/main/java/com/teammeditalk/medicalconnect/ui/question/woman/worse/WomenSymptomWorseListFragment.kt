package com.teammeditalk.medicalconnect.ui.question.inner.worse

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInnerSymptomWorseListBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WomenSymptomWorseListFragment :
    BaseFragment<FragmentInnerSymptomWorseListBinding>(
        FragmentInnerSymptomWorseListBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()
    private val selectedWorseList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.layoutConstraint.apply {
            for (child in this.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    (it as SelectBox).updateSelected(it.isSelected)
                    if (it.isSelected) {
                        selectedWorseList.add(it.getContent())
                    } else {
                        selectedWorseList.remove(it.getContent())
                    }
                }
            }
        }
        binding.btnNext.setOnClickListener {
            viewModel.selectWorseList(selectedWorseList)
            navController.navigate(R.id.pregnancyCheckFragment)
        }
    }
}

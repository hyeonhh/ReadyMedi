package com.teammeditalk.medicalconnect.ui.question.common.effect

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomWorseBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

// 통증을 악화시키는 요인
@AndroidEntryPoint
class SelectSymptomWorseFragment :
    BaseFragment<FragmentSelectSymptomWorseBinding>(
        FragmentSelectSymptomWorseBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val worseList = mutableListOf<String>()
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            for (child in layoutConstraint.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    (it as SelectBox).updateSelected(it.isSelected)
                    if (it.isSelected) {
                        worseList.add(it.getContent())
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.selectWorseList(worseList)
            navController.navigate(R.id.selectSymptomBetterFragment)
        }
    }
}

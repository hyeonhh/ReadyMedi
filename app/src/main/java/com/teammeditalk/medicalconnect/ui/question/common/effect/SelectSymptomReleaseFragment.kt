package com.teammeditalk.medicalconnect.ui.question.common.effect

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomReleaseBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSymptomReleaseFragment :
    BaseFragment<FragmentSelectSymptomReleaseBinding>(
        FragmentSelectSymptomReleaseBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val releaseList = mutableListOf<String>()
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            for (child in layoutConstraint.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    (it as SelectBox).updateSelected(it.isSelected)
                    if (it.isSelected) {
                        releaseList.add(it.getContent())
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.selectReleaseList(releaseList)
            navController.navigate(R.id.selectOtherSymptomFragment)
        }
    }
}

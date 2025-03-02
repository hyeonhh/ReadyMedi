package com.teammeditalk.medicalconnect.ui.question.general.worse

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentGeneralSymptomWorseListBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralSymptomWorseListFragment :
    BaseFragment<FragmentGeneralSymptomWorseListBinding>(
        FragmentGeneralSymptomWorseListBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val selectedWorseList = mutableListOf<String>()
    private val viewModel: QuestionViewModel by activityViewModels()

    private fun onChipClick(view: View) {
        view as Chip
        view.isSelected = !view.isSelected
        if (view.isSelected) {
            view.setChipStrokeColorResource(R.color.blue50)
            view.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue50))
        } else {
            view.setChipStrokeColorResource(R.color.gray10)
            view.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray95))
        }
    }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            viewModel.selectWorseList(selectedWorseList)
            navController.navigate(R.id.generalOtherSymptomFragment)
        }
        binding.layoutConstraint.apply {
            for (child in this.children) {
                child.setOnClickListener {
                    onChipClick(it)
                    if (it.isSelected) {
                        (it as SelectBox).updateSelected(true)
                        selectedWorseList.add(it.getContent())
                    } else {
                        (it as SelectBox).updateSelected(false)
                        selectedWorseList.remove(it.getContent())
                    }
                }
            }
        }
    }
}

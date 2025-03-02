package com.teammeditalk.medicalconnect.ui.question.dental.fragment.worse

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDentalWorseBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DentalSelectWorseListFragment :
    BaseFragment<FragmentSelectDentalWorseBinding>(
        FragmentSelectDentalWorseBinding::inflate,
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

        binding.btnNext.setOnClickListener {
            viewModel.selectWorseList(selectedWorseList)
            val action = DentalSelectWorseListFragmentDirections.actionDentalSelectWorseListFragmentToDentalSelectOtherSymptomFragment("치과")
            navController.navigate(action)
        }
    }
}

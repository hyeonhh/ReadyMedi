package com.teammeditalk.medicalconnect.ui.question.dental.fragment.worse

import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectDentalWorseBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
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
    private val worseListByKorean = mutableListOf<String>()
    private val idList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.layoutConstraint.apply {
            for (child in this.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    if (it.isSelected) {
                        (it as SelectBox).updateSelected(true)
                        selectedWorseList.add(it.getContent())
                        worseListByKorean.add(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
                        idList.add(it.tag.toString())
                    } else {
                        (it as SelectBox).updateSelected(false)
                        selectedWorseList.remove(it.getContent())
                        worseListByKorean.remove(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
                        idList.remove(it.tag.toString())
                    }
                }
            }
        }

        binding.btnNext.setOnClickListener {
            if (selectedWorseList.isEmpty()) {
                binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
            } else {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                viewModel.selectWorseList(selectedWorseList)
                viewModel.setWorseListId(idList)
                viewModel.setInnerWorstListByKorean(worseListByKorean)
                val action =
                    DentalSelectWorseListFragmentDirections.actionDentalSelectWorseListFragmentToDentalSelectOtherSymptomFragment(
                        "치과",
                    )
                navController.navigate(action)
            }
        }
    }
}

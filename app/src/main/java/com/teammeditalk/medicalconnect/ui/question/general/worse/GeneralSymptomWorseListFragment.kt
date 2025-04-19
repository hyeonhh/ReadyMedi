package com.teammeditalk.medicalconnect.ui.question.general.worse

import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentGeneralSymptomWorseListBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.ResourceUtil
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
    private val worseListByKorean = mutableListOf<String>()
    private val worseIdList = mutableListOf<String>()

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
                        worseIdList.add(it.tag.toString())
                    } else {
                        (it as SelectBox).updateSelected(false)
                        selectedWorseList.remove(it.getContent())
                        worseListByKorean.remove(ResourceUtil.getKoreanString(requireContext(), it.tag.toString()))
                        worseIdList.remove(it.tag.toString())
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            if (selectedWorseList.isEmpty()) {
                binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
            } else {
                binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                viewModel.setWorseListId(worseIdList)
                viewModel.selectWorseList(selectedWorseList)
                viewModel.setInnerWorstListByKorean(worseListByKorean)
                navController.navigate(R.id.generalOtherSymptomFragment)
            }
        }
    }
}

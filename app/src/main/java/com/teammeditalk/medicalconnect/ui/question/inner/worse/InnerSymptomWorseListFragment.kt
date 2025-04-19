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
class InnerSymptomWorseListFragment :
    BaseFragment<FragmentInnerSymptomWorseListBinding>(
        FragmentInnerSymptomWorseListBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()
    private val selectedWorseList = mutableListOf<String>()
    private val worstListByKorean = mutableListOf<String>()
    private val worstIdList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.layoutConstraint.apply {
            for (child in this.children) {
                child.setOnClickListener {
                    it.isSelected = !it.isSelected
                    if (it.isSelected) {
                        (it as SelectBox).updateSelected(true)
                        selectedWorseList.add(it.getContent())
                        worstListByKorean.add(it.getKoreanString(it.tag.toString()))
                        worstIdList.add(it.tag.toString())
                        worstIdList
                    } else {
                        (it as SelectBox).updateSelected(false)
                        selectedWorseList.remove(it.getContent())
                        worstListByKorean.remove(it.getKoreanString(it.tag.toString()))
                        worstIdList.remove(it.tag.toString())
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            viewModel.setInnerWorstListByKorean(worstListByKorean)
            viewModel.selectWorseList(selectedWorseList)
            viewModel.setWorseListId(worstIdList)
            navController.navigate(R.id.innerOtherSymptomFragment)
        }
    }
}

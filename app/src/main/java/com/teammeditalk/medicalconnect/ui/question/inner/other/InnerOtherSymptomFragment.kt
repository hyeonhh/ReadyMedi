package com.teammeditalk.medicalconnect.ui.question.inner.other

import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInnerOtherSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InnerOtherSymptomFragment :
    BaseFragment<FragmentInnerOtherSymptomBinding>(
        FragmentInnerOtherSymptomBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()
    private var isOtherSymptom: Boolean = false
    private val selectedOtherList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.chipGroupSymptoms.apply {
            for (child in this.children) {
                child.setOnClickListener {
                    if (it.isSelected) {
                        selectedOtherList.add((it as SelectBox).getContent())
                    } else {
                        selectedOtherList.remove((it as SelectBox).getContent())
                    }
                }
            }
        }

        binding.selectBoxYes.setOnClickListener {
            isOtherSymptom = true
            binding.selectBoxYes.updateSelected(true)
            binding.selectBoxNo.updateSelected(false)
        }
        binding.selectBoxNo.setOnClickListener {
            isOtherSymptom = false
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.setOtherSymptomList(selectedOtherList)
            navController.navigate(R.id.innerAdditionalInputFragment)
        }
    }
}

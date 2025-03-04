package com.teammeditalk.medicalconnect.ui.question.inner.other

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInnerOtherSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
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

        binding.selectBoxYes.setOnClickListener {
            isOtherSymptom = true
            binding.selectBoxYes.updateSelected(true)
            binding.selectBoxNo.updateSelected(false)
            binding.tvSymptomTitle.visibility = View.VISIBLE
            binding.chipGroup.visibility = View.VISIBLE
        }
        binding.selectBoxNo.setOnClickListener {
            binding.selectBoxYes.updateSelected(false)
            binding.selectBoxNo.updateSelected(true)
            isOtherSymptom = false
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            binding.chipGroup.apply {
                for (child in this.checkedChipIds) {
                    val chip = findViewById<Chip>(child)
                    selectedOtherList.add(chip.text.toString())
                }

                viewModel.setOtherSymptomList(selectedOtherList)
                navController.navigate(R.id.innerAdditionalInputFragment)
            }
        }
    }
}

package com.teammeditalk.medicalconnect.ui.question.common.other

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectOtherSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectJointOtherSymptomFragment :
    BaseFragment<FragmentSelectOtherSymptomBinding>(
        FragmentSelectOtherSymptomBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()
    private val selectedOtherSymptomList = mutableListOf<String>()

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxYes.setOnClickListener {
                (it as SelectBox).updateSelected(true)
                selectBoxNo.updateSelected(false)
                tvSymptomTitle.visibility = View.VISIBLE
                chipGroup.visibility = View.VISIBLE
            }

            selectBoxNo.setOnClickListener {
                (it as SelectBox).updateSelected(true)
                selectBoxYes.updateSelected(false)
            }

            btnBack.setOnClickListener {
                navController.popBackStack()
            }

            btnNext.setOnClickListener {
                with(chipGroup) {
                    for (id in this.checkedChipIds) {
                        val chip = findViewById<Chip>(id)
                        selectedOtherSymptomList.add(chip.text.toString())
                    }
                }

                viewModel.setOtherSymptomList(selectedOtherSymptomList)
                // todo : 동반 증상 저장하기
                val bundle = bundleOf("hospital_type" to "정형")
                navController.navigate(R.id.jointAdditionalInputFragment, bundle)
            }
        }
    }
}

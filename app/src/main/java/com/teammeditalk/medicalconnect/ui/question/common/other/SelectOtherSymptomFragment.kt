package com.teammeditalk.medicalconnect.ui.question.common.other

import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectOtherSymptomBinding
import com.teammeditalk.medicalconnect.ui.util.SelectBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectOtherSymptomFragment :
    BaseFragment<FragmentSelectOtherSymptomBinding>(
        FragmentSelectOtherSymptomBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        with(binding) {
            selectBoxYes.setOnClickListener {
                (it as SelectBox).updateSelected(true)
                selectBoxNo.updateSelected(false)
            }

            selectBoxNo.setOnClickListener {
                (it as SelectBox).updateSelected(true)
                selectBoxYes.updateSelected(false)
            }

            btnBack.setOnClickListener {
                navController.popBackStack()
            }

            btnNext.setOnClickListener {
                navController.navigate(R.id.additionalInputFragment)
            }
        }
    }
}

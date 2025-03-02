package com.teammeditalk.medicalconnect.ui.question.inner.type

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentInnerSymptomTypeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WomenSymptomTypeFragment :
    BaseFragment<FragmentInnerSymptomTypeBinding>(
        FragmentInnerSymptomTypeBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            val bundle = bundleOf("hospital_type" to "산부")
            navController.navigate(R.id.selectWomenPainDegreeFragment, bundle)
        }
    }
}

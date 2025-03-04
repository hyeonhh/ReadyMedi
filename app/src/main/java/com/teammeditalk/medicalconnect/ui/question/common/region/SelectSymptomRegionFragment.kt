package com.teammeditalk.medicalconnect.ui.question.dental.fragment.region

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomRegionBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSymptomRegionFragment :
    BaseFragment<FragmentSelectSymptomRegionBinding>(
        FragmentSelectSymptomRegionBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private val args by navArgs<SelectSymptomRegionFragmentArgs>()

    override fun onBindLayout() {
        super.onBindLayout()
        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            when (args.hospitalType) {
                "내과" -> {
                    val bundle = bundleOf("hospital_type" to "내과")
                    navController.navigate(R.id.selectInnerStartFragment, bundle)
                }
                "일반" -> {
                    val bundle = bundleOf("hospital_type" to "일반")
                    navController.navigate(R.id.selectGeneralSymptomStartFragment, bundle)
                }
                "정형" -> {
                    val bundle = bundleOf("hospital_type" to "정형")
                    navController.navigate(R.id.selectJointSymptomStartFragment, bundle)
                }
            }
        }
    }
}

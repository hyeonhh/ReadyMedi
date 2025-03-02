package com.teammeditalk.medicalconnect.ui.question.common.degree

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectPainDegreeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPainDegreeFragment :
    BaseFragment<FragmentSelectPainDegreeBinding>(
        FragmentSelectPainDegreeBinding::inflate,
    ) {
    private var degree: Float = 0.0F
    private val viewModel: QuestionViewModel by activityViewModels()
    private val args by navArgs<SelectPainDegreeFragmentArgs>()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.slider.addOnChangeListener { _, value, _ ->
            degree = value
        }

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnNext.setOnClickListener {
            viewModel.selectDegree(degree)
            when (args.hospitalType) {
                "내과" -> {
                    val bundle = bundleOf("hospital_type" to "내과")
                    navController.navigate(R.id.selectInnerDurationFragment, bundle)
                }
                "일반" -> {
                    val bundle = bundleOf("hospital_type" to "일반")
                    navController.navigate(R.id.selectGeneralDurationFragment, bundle)
                }
                "치과" -> {
                    val bundle = bundleOf("hospital_type" to "치과")
                    navController.navigate(R.id.selectDentalDurationFragment, bundle)
                }
                "정형" -> {
                    val bundle = bundleOf("hospital_type" to "정형")
                    navController.navigate(R.id.selectJointDurationFragment, bundle)
                }
                "산부" -> {
                    val bundle = bundleOf("hospital_type" to "산부")
                    navController.navigate(R.id.selectWomenDurationFragment, bundle)
                }
            }
        }
    }
}

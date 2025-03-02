package com.teammeditalk.medicalconnect.ui.question.common.input

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAdditionalInputBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdditionalInputFragment :
    BaseFragment<FragmentAdditionalInputBinding>(
        FragmentAdditionalInputBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val viewModel: QuestionViewModel by activityViewModels()
    private val args by navArgs<AdditionalInputFragmentArgs>()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener { navController.popBackStack() }
        binding.btnComplete.setOnClickListener {
            viewModel.setAdditionalInput(binding.editText.getContent())
            when (args.hospitalType) {
                "내과" -> {
                    val bundle = bundleOf("hospital_type" to "내과")
                    navController.navigate(R.id.innerLoadingFragment)
                }
                "치과" -> {
                    val bundle = bundleOf("hospital_type" to "치과")
                    navController.navigate(R.id.dentalLoadingFragment, bundle)
                }
                "일반" -> {
                    val bundle = bundleOf("hospital_type" to "일반")
                    navController.navigate(R.id.generalLoadingFragment2, bundle)
                }
                "정형" -> {
                    val bundle = bundleOf("hospital_type" to "정형")
                    navController.navigate(R.id.jointLoadingFragment, bundle)
                }
                "산부" -> {
                    val bundle = bundleOf("hospital_type" to "산부")
                    navController.navigate(R.id.womenLoadingFragment, bundle)
                }
            }
        }
    }
}

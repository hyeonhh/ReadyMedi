package com.teammeditalk.medicalconnect.ui.question.common.loading

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLoadingBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingFragment :
    BaseFragment<FragmentLoadingBinding>(
        FragmentLoadingBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val args by navArgs<LoadingFragmentArgs>()
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        viewModel.translateFailure.observe(this, {
            navController.popBackStack()
            Toast.makeText(requireContext(), "잠시 후에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
        })

        viewModel.translateEvent.observe(this, {
            // 3초 지연 주기
            lifecycleScope.launch {
                delay(3000)
            }
            when (args.hospitalType) {
                "내과" -> {
                    navController.navigate(R.id.innerSymptomResultFragment)
                }
                "치과" -> {
                    navController.navigate(R.id.dentalSymptomResultFragment)
                }
                "일반" -> {
                    navController.navigate(R.id.generalSymptomResultFragment)
                }
                "정형" -> {
                    navController.navigate(R.id.fragmentJointSymptomResult)
                }
                "산부" -> {
                    navController.navigate(R.id.womenSymptomResultFragment)
                }
            }
        })
    }
}

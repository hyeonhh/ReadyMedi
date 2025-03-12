package com.teammeditalk.medicalconnect.ui.history.symptom

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentMySymptomHistoryBinding
import com.teammeditalk.medicalconnect.ui.history.symptom.adapter.ClickListener
import com.teammeditalk.medicalconnect.ui.history.symptom.adapter.SymptomHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MySymptomHistoryFragment :
    BaseFragment<FragmentMySymptomHistoryBinding>(
        FragmentMySymptomHistoryBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private val adapter by lazy { SymptomHistoryAdapter() }
    private val viewModel: MySymptomHistoryViewModel by viewModels()

    override fun onBindLayout() {
        super.onBindLayout()
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(
            object : ClickListener {
                override fun onClick(
                    timeStamp: String,
                    hospitalType: String,
                ) {
                    Timber.d("클릭 :$timeStamp")
                    when (hospitalType) {
                        "치과" -> {
                            val action =
                                MySymptomHistoryFragmentDirections.actionMySymptomHistoryFragmentToDentalSymptomFragment(
                                    timeStamp,
                                    viewModel.userId.value,
                                )
                            navController.navigate(action)
                        }
                        "내과" -> {
                            val action =
                                MySymptomHistoryFragmentDirections.actionMySymptomHistoryFragmentToInnerSymptomFragment(
                                    timeStamp,
                                    viewModel.userId.value,
                                )
                            navController.navigate(action)
                        }
                        "일반" -> {
                            val action =
                                MySymptomHistoryFragmentDirections.actionMySymptomHistoryFragmentToGeneralSymptomFragment(
                                    timeStamp,
                                    viewModel.userId.value,
                                )
                            navController.navigate(action)
                        }
                        "정형" -> {
                            val action =
                                MySymptomHistoryFragmentDirections.actionMySymptomHistoryFragmentToJointSymptomFragment(
                                    timeStamp,
                                    viewModel.userId.value,
                                )
                            navController.navigate(action)
                        }
                        "산부" -> {
                            val action =
                                MySymptomHistoryFragmentDirections.actionMySymptomHistoryFragmentToJointSymptomFragment(
                                    timeStamp,
                                    viewModel.userId.value,
                                )
                            navController.navigate(action)
                        }
                    }
                }
            },
        )
        lifecycleScope.launch {
            viewModel.history.collectLatest {
                if (it.isEmpty()) {
                    binding.lottie.visibility = View.GONE
                    binding.emptyView.emptyLayout.visibility = View.VISIBLE
                } else {
                    binding.emptyView.emptyLayout.visibility = View.GONE
                    binding.lottie.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.setList(it)
                }
            }
        }
        binding.emptyView.btnGoToSelectSymptom.setOnClickListener {
            navController.navigate(R.id.question_nav)
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
    }
}

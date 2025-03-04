package com.teammeditalk.medicalconnect.ui.history.symptom

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentMySymptomHistoryBinding
import com.teammeditalk.medicalconnect.ui.history.symptom.adapter.SymptomHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            viewModel.history.collectLatest {
                if (it.isNotEmpty()) {
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

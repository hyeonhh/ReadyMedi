package com.teammeditalk.medicalconnect.ui.home

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentHomeBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(
        FragmentHomeBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnTranslate.setOnClickListener {
            navController.navigate(R.id.symptomResultTranslateFragment)
        }

        binding.btn1.setOnClickListener {
            val intent = Intent(context, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}

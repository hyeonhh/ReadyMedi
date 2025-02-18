package com.teammeditalk.medicalconnect.ui.onboarding

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.MainActivity
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentDiseaseSelectBinding

class DiseaseSelectFragment :
    BaseFragment<FragmentDiseaseSelectBinding>(
        FragmentDiseaseSelectBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }

    override fun onBindLayout() {
        super.onBindLayout()
        binding.materialButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

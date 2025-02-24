package com.teammeditalk.medicalconnect.ui.onboarding.language

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLanguageSelectBinding
import com.teammeditalk.medicalconnect.ui.language.LanguageUtil
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageSelectFragment :
    BaseFragment<FragmentLanguageSelectBinding>(
        FragmentLanguageSelectBinding::inflate,
    ) {
    private val viewModel: OnBoardingViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private var selectedLanguage: String? = null

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnNext.setOnClickListener {
            if (selectedLanguage != null) {
                viewModel.saveUserLanguage(selectedLanguage!!)
                requireContext().let {
                    LanguageUtil.setLocale(it, selectedLanguage!!)
                    requireActivity().recreate()
                }
                navController.navigate(R.id.diseaseSelectFragment)
            } else {
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.VISIBLE
            }
        }

        with(binding) {
            selectBoxEnglish.setOnClickListener {
                selectBoxEnglish.updateSelected(true)
                selectBoxChinese.updateSelected(false)
                selectBoxJapanese.updateSelected(false)
                selectedLanguage = "en"
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.GONE
            }

            selectBoxJapanese.setOnClickListener {
                selectBoxJapanese.updateSelected(true)
                selectBoxEnglish.updateSelected(false)
                selectBoxChinese.updateSelected(false)
                selectedLanguage = "ja"
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.GONE
            }

            selectBoxChinese.setOnClickListener {
                selectBoxChinese.updateSelected(true)
                selectBoxEnglish.updateSelected(false)
                selectBoxJapanese.updateSelected(false)
                selectedLanguage = "zh"
                binding.layoutLanguageWarn.layoutLanguageWarn.visibility = View.GONE
            }
        }
    }
}

package com.teammeditalk.medicalconnect.ui.onboarding

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLanguageSelectBinding
import com.teammeditalk.medicalconnect.ui.language.LanguageUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
//                viewModel.setLanguage(selectedLanguage!!)
                requireContext().let {
                    LanguageUtil.setLocale(it, selectedLanguage!!)
                    Timber.d("선택된 언어 :$selectedLanguage")
                    requireActivity().recreate()
                }
                navController.navigate(R.id.diseaseSelectFragment)
            }
        }

        binding.btnEnglish.setOnClickListener {
            it.isSelected = !it.isSelected
            selectedLanguage = "en"
        }
        binding.btnJapanese.setOnClickListener {
            it.isSelected = !it.isSelected
            selectedLanguage = "ja"
        }
        binding.btnChinese.setOnClickListener {
            it.isSelected = !it.isSelected
            selectedLanguage = "zh"
        }
    }
}

package com.teammeditalk.medicalconnect.ui.translate

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSypmtomResultTranslateBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SymptomResultTranslateFragment :
    BaseFragment<FragmentSypmtomResultTranslateBinding>(
        FragmentSypmtomResultTranslateBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()

        setUpTranslator()
    }

    private fun setUpTranslator() {
        binding.tvTest.text = "이 부위가 바늘로 찌르는 것처럼 콕콕 아파요"
        val symptomPrompt = "이 부위가 바늘로 찌르는 것처럼 콕콕 아파요"
        val options =
            TranslatorOptions
                .Builder()
                .setSourceLanguage(TranslateLanguage.KOREAN)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
        val englishKoreanTranslator = Translation.getClient(options)
        val conditions =
            DownloadConditions
                .Builder()
                .requireWifi()
                .build()
        englishKoreanTranslator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                englishKoreanTranslator
                    .translate(symptomPrompt)
                    .addOnSuccessListener {
                        binding.tvResult.text = it
                        Timber.d("success to translate :$it")
                    }.addOnFailureListener {
                        Timber.d("failed to translate :${it.message}")
                    }
            }.addOnFailureListener { exception ->
                Timber.d("failed to download model :${exception.message}")
            }
    }
}

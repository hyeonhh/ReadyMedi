package com.teammeditalk.medicalconnect.ui.question.loading

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentLoadingBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class LoadingFragment :
    BaseFragment<FragmentLoadingBinding>(
        FragmentLoadingBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()

    private fun setUpTranslator(content: String) {
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
                    .translate(content)
                    .addOnSuccessListener {
                        Timber.d("success to translate :$it")
                        findNavController().navigate(R.id.symptomResultFragment)
                    }.addOnFailureListener {
                        Timber.d("failed to translate :${it.message}")
                    }
            }.addOnFailureListener { exception ->
                Timber.d("failed to download model :${exception.message}")
            }
    }

    override fun onBindLayout() {
        super.onBindLayout()
        // 증상
        lifecycleScope.launch {
            viewModel.selectedSymptom.collectLatest {
                setUpTranslator(it.second)
            }
        }
    }
}

package com.teammeditalk.medicalconnect.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSypmtomResultTranslateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class SymptomResultTranslateFragment :
    BaseFragment<FragmentSypmtomResultTranslateBinding>(
        FragmentSypmtomResultTranslateBinding::inflate,
    ) {
    lateinit var viewbinding: FragmentSypmtomResultTranslateBinding

    // 텍스트 인식 시작
    val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewbinding = FragmentSypmtomResultTranslateBinding.inflate(layoutInflater)
        val view = viewbinding.root
        return view
    }

    private fun recognizeText(image: InputImage) {
        lifecycleScope.launch(Dispatchers.Main) {
            recognizer
                .process(image)
                .addOnSuccessListener {
                    Timber.d("success recognize :$it")
                }.addOnFailureListener {
                    Timber.d("failed recognize :${it.message}")
                }
        }
    }

    override fun onStop() {
        super.onStop()
        recognizer.close()
    }

    override fun onBindLayout() {
        super.onBindLayout()
        binding.tvTest
    }
}

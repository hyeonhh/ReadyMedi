package com.teammeditalk.medicalconnect

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpTranslator()

    }
}

private fun setUpTranslator() {
    val symptomPrompt = "이 부위가 바늘로 찌르는 것처럼 콕콕 아파요"
    val diseasePrompt = "갑상선 저하증이 있어요"

    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.KOREAN)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()
    val englishKoreanTranslator = Translation.getClient(options)

    val conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()
    englishKoreanTranslator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener {
            englishKoreanTranslator.translate(symptomPrompt)
                .addOnSuccessListener {
                    Timber.d("success to translate :${it}")

                }
                .addOnFailureListener {
                    Timber.d("failed to translate :${it.message}")

                }
        }
        .addOnFailureListener { exception ->
            Timber.d("failed to download model :${exception.message}")
        }
}
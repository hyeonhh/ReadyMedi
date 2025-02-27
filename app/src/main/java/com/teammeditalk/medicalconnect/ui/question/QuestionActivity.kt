package com.teammeditalk.medicalconnect.ui.question

import androidx.activity.viewModels
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityQuestionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionActivity :
    BaseActivity<ActivityQuestionBinding>(
        ActivityQuestionBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by viewModels()

    override fun onBindLayout() {
        super.onBindLayout()
    }
}

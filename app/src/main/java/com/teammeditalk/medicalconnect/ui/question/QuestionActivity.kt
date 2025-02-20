package com.teammeditalk.medicalconnect.ui.question

import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityQuestionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionActivity :
    BaseActivity<ActivityQuestionBinding>(
        ActivityQuestionBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()
    }
}

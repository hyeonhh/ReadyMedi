package com.teammeditalk.medicalconnect.ui.question.ds

import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSideEffectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicationSideEffectFragment :
    BaseFragment<FragmentSideEffectBinding>(
        FragmentSideEffectBinding::inflate,
    )

package com.teammeditalk.medicalconnect.ui.question.ds

import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentAnesthesiaHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnesthesiaHistoryFragment :
    BaseFragment<FragmentAnesthesiaHistoryBinding>(
        FragmentAnesthesiaHistoryBinding::inflate,
    )

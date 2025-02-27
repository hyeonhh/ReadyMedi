package com.teammeditalk.medicalconnect.ui.result

import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSymptomResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SymptomResultFragment :
    BaseFragment<FragmentSymptomResultBinding>(
        FragmentSymptomResultBinding::inflate,
    ) {
    private fun showToolTip() {
        val balloon =
            context?.let {
                createBalloon(it) {
                    setText("의료진에게 보여줄 땐 여길 눌러주세요")
                    setMarginRight(20)
                    setPadding(10)
                    setHeight(BalloonSizeSpec.WRAP)
                    setWidth(BalloonSizeSpec.WRAP)
                    setTextColorResource(R.color.white)
                    setCornerRadius(30f)
                    setBackgroundColorResource(R.color.orange50)
                    build()
                }
            }
        balloon?.showAlignTop(binding.btnSwitch)
    }

    override fun onBindLayout() {
        super.onBindLayout()
        showToolTip()
    }
}

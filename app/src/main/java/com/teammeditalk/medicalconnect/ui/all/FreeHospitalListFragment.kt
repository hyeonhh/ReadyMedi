package com.teammeditalk.medicalconnect.ui.all

import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentFreeHospitalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FreeHospitalListFragment :
    BaseFragment<FragmentFreeHospitalBinding>(
        FragmentFreeHospitalBinding::inflate,
    ) {
    override fun onBindLayout() {
        super.onBindLayout()
        binding.webview.getSettings().setUseWideViewPort(true)
        binding.webview.loadUrl(
            "https://global.seoul.go.kr/web/news/senw/bordContDetail.do?mode=W&brd_no=5&post_no=FCDFA5D8CA2A0184E053C0A8A0231371&lang=ko",
        )
    }
}

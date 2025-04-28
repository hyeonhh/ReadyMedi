package com.teammeditalk.medicalconnect.ui.user

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentUserBinding
import com.teammeditalk.medicalconnect.ui.onboarding.language.LanguageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment :
    BaseFragment<FragmentUserBinding>(
        FragmentUserBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    val auth = Firebase.auth
    private val currentUser = auth.currentUser
    private val viewModel: UserViewModel by viewModels()

    override fun onBindLayout() {
        super.onBindLayout()
        // 앱 언어  스피너
        val language = arrayOf("English", "Japanese", "Chinese", "Korean")
        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                language,
            )

        binding.spinner.adapter = adapter

        lifecycleScope.launch {
            viewModel.language.collectLatest {
                when (it) {
                    "en" -> binding.spinner.setSelection(0)
                    "ja" -> binding.spinner.setSelection(1)
                    "zh" -> binding.spinner.setSelection(2)
                    "ko" -> binding.spinner.setSelection(3)
                }
            }
        }

        // todo :  스피너 앱 언어 변경 이벤트
        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    // 선택한 언어로 앱 언어 변경하기
                    when (position) {
                        0 -> {
                            val locale = LocaleListCompat.forLanguageTags("en")
                            LanguageUtil.setApplicationLocales(locale)
                            // 뷰모델에 저장된 언어 변경하기
                            viewModel.modifyLanguage("en")
                        }
                        1 -> {
                            val locale = LocaleListCompat.forLanguageTags("ja")
                            LanguageUtil.setApplicationLocales(locale)
                            viewModel.modifyLanguage("ja")
                        }
                        2 -> {
                            val locale = LocaleListCompat.forLanguageTags("zh")
                            LanguageUtil.setApplicationLocales(locale)
                            viewModel.modifyLanguage("zh")
                        }
                        3 -> {
                            val locale = LocaleListCompat.forLanguageTags("ko")
                            LanguageUtil.setApplicationLocales(locale)
                            viewModel.modifyLanguage("ko")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        if (currentUser != null) {
            binding.btnLogin.text = "로그아웃"
        } else {
            binding.btnLogin.text = "로그인"
        }

        lifecycleScope.launch {
            viewModel.nickName.collectLatest {
                binding.tvUserNickname.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.profileUri.collectLatest {
                // Glide.with(requireActivity()).load(it).into(binding.ivUserProfile)
            }
        }

        binding.btnLogin.setOnClickListener {
            // 로그아웃
            auth.signOut()
            viewModel.deleteUserData()
            // todo 재로그인 시 uid 없음 -> 재 시도 시 Uid가 생김
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
    }
}

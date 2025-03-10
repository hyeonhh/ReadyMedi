package com.teammeditalk.medicalconnect.ui.user

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentUserBinding
import com.teammeditalk.medicalconnect.ui.auth.AuthActivity
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

            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener { navController.popBackStack() }
    }
}

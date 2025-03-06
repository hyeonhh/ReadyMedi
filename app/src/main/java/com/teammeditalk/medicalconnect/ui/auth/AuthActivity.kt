package com.teammeditalk.medicalconnect.ui.auth

import android.content.Intent
import android.credentials.GetCredentialResponse
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityAuthBinding
import com.teammeditalk.medicalconnect.ui.main.MainActivity
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity :
    BaseActivity<ActivityAuthBinding>(
        ActivityAuthBinding::inflate,
    ),
    GestureDetector.OnGestureListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager
    private val viewModel: AuthViewModel by viewModels()
    private var fragmentNum = 0

    private lateinit var gestureDetector: GestureDetector

    override fun onBindLayout() {
        super.onBindLayout()

        gestureDetector = GestureDetector(this, this)

        binding.authLayout.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        auth = Firebase.auth

        lifecycleScope.launch {
            viewModel.uid.collectLatest {
                if (it != "") {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        credentialManager = CredentialManager.create(this)

        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption
                .Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(true)
                .setServerClientId(BuildConfig.FirebaseClientId)
                .build()

        val request: GetCredentialRequest =
            GetCredentialRequest
                .Builder()
                .addCredentialOption(googleIdOption)
                .build()

        credentialManager = CredentialManager.create(this)

        binding.btnGoogleLogin.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val result =
                        credentialManager.getCredential(
                            request = request,
                            context = this@AuthActivity,
                        )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {
                    e.printStackTrace()
                    Timber.d("failed to get credential :${e.message}")
                }
            }
        }
    }

    private fun handleSignIn(result: androidx.credentials.GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential
                                .createFrom(credential.data)

                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                        val uid = auth.uid
                        val currentUser = auth.currentUser
                        lifecycleScope.launch {
                            if (uid != null) {
                                viewModel.saveUid(uid)
                            }
                            if (currentUser != null) {
                                val photoUrl = auth.currentUser!!.photoUrl
                                val nickName = auth.currentUser!!.displayName
                                viewModel.saveProfileUrl(photoUrl.toString(), nickName.toString())
                            }
                        }
                        Timber.d("uid :$uid")
                    } catch (e: GoogleIdTokenParsingException) {
                        e.printStackTrace()
                        Timber.d("Received an invalid google id token response :${e.message}")
                    }
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth
            .signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    Timber.d("성공 :$user")
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                } else {
                    Timber.e("signInWithCredential:failure ${it.exception}")
                }
            }
    }

    // 다른 필수 메서드들 구현 (간략화를 위해 생략)
    override fun onDown(e: MotionEvent): Boolean = true

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent): Boolean = false

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float,
    ): Boolean = false

    override fun onLongPress(e: MotionEvent) {}

    // GestureDetector.OnGestureListener 구현
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float,
    ): Boolean {
        // e1: 시작 포인트, e2: 끝 포인트
        try {
            val diffX = e2.x - e1!!.x
            val diffY = e2.y - e1.y

            if (Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > 80 && Math.abs(velocityX) > 100) {
                if (diffX < 0) {
                    when (fragmentNum) {
                        1 -> {
                            binding.tvTitle.text = "증상을 미리 입력하면\n어려운 병원과 약국도 걱정없어요"
                            binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding_group2))
                            binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator2))
                            fragmentNum = 2
                        }
                        2 -> {
                            binding.tvTitle.text = "외국어 가능 의료기관부터\n외국인 보험 정보까지 쏙쏙"
                            binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding3))
                            binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator3))
                            fragmentNum = 0
                        }
                        0 -> {
                            binding.tvTitle.text = "낯선 한국 병원과 약국\n당신의 의료 가이드 Ready Medi"
                            binding.ivImage.setImageDrawable(getDrawable(R.drawable.ic_onboarding_group1))
                            binding.ivStateBar.setImageDrawable(getDrawable(R.drawable.ic_indicator1))
                            fragmentNum = 1
                        }
                    }
                    // 오른쪽에서 왼쪽으로 스와이프
                    Timber.d("스와이프")
                } else {
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }
}

package com.teammeditalk.medicalconnect.ui.auth

import android.content.Intent
import android.credentials.GetCredentialResponse
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
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityAuthBinding
import com.teammeditalk.medicalconnect.ui.main.MainActivity
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity :
    BaseActivity<ActivityAuthBinding>(
        ActivityAuthBinding::inflate,
    ) {
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager
    private val viewModel: AuthViewModel by viewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        auth = Firebase.auth
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
}

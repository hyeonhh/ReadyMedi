package com.teammeditalk.medicalconnect.ui.auth.onboarging

import android.content.Intent
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.teammeditalk.medicalconnect.BuildConfig
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentOnboarding1Binding
import com.teammeditalk.medicalconnect.ui.auth.AuthViewModel
import com.teammeditalk.medicalconnect.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FirstOnboardingFragment :
    BaseFragment<FragmentOnboarding1Binding>(
        FragmentOnboarding1Binding::inflate,
    ) {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

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
                        Timber.d("uid :$uid")
                        lifecycleScope.launch {
                            if (uid != null) {
                                Timber.d("uid :$uid")
                                viewModel.saveUid(uid)
                            } else {
                                Timber.d("uid 없음 :$uid")
                            }
                            if (currentUser != null) {
                                val photoUrl = auth.currentUser!!.photoUrl
                                val nickName = auth.currentUser!!.displayName
                                viewModel.saveProfileUrl(photoUrl.toString(), nickName.toString())
                            }
                        }
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
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    Timber.d("성공 :$user")
                    val intent = Intent(requireContext(), OnBoardingActivity::class.java)
                    startActivity(intent)
                } else {
                    Timber.e("signInWithCredential:failure ${it.exception}")
                }
            }
    }

    override fun onBindLayout() {
        super.onBindLayout()
        credentialManager = CredentialManager.create(requireContext())
        auth = Firebase.auth

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

        binding.btnGoogleLogin.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val result =
                        credentialManager.getCredential(
                            request = request,
                            context = requireContext(),
                        )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {
                    e.printStackTrace()
                    Timber.d("failed to get credential :${e.message}")
                }
            }
        }
    }
}

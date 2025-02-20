package com.teammeditalk.medicalconnect.ui.onboarding

import android.content.Context
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.fragment.NavHostFragment
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.UserHealthInfo
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.data.serializer.UserPreferencesSerializer
import com.teammeditalk.medicalconnect.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity :
    BaseActivity<ActivityOnboardingBinding>(
        ActivityOnboardingBinding::inflate,
    ) {
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navHostFragment.navController
    }

    private val Context.userPreferencesStore: DataStore<UserHealthInfo> by dataStore(
        fileName = "user_prefs.pb",
        serializer = UserPreferencesSerializer,
    )

    override fun onBindLayout() {
        super.onBindLayout()
    }
}

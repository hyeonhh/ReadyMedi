package com.teammeditalk.medicalconnect.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityMainBinding
import com.teammeditalk.medicalconnect.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding>(
        ActivityMainBinding::inflate,
    ) {
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            viewModel.uid.collectLatest {
                if (it == "-1" || it == "") {
                    Timber.d("uid 없음", it)
                    val intent = Intent(this@MainActivity, AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.homeFragment
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.findNavController()

        // 바텀바 유무 적용하기
        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.editDiseaseFragment, R.id.myHealthInfoFragment, R.id.mySymptomHistoryFragment, R.id.selectSymptomFragment,
                    R.id.selectRegionFragment,
                    -> {
                        binding.bottomNavigation.visibility = View.GONE
                    }
                    else -> {
                        binding.bottomNavigation.visibility = View.VISIBLE
                    }
                }
            }
        binding.bottomNavigation
            .setupWithNavController(navController)
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.location -> {
                    navController.navigate(R.id.mapFragment)
                    true
                }
                R.id.all -> {
                    navController.navigate(R.id.allFragment2)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}

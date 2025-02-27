package com.teammeditalk.medicalconnect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.teammeditalk.medicalconnect.base.BaseActivity
import com.teammeditalk.medicalconnect.databinding.ActivityMainBinding
import com.teammeditalk.medicalconnect.ui.home.HomeFragment
import com.teammeditalk.medicalconnect.ui.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding>(
        ActivityMainBinding::inflate,
    ) {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.homeFragment
        }
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, HomeFragment())
                        .commit()
                    true
                }
                R.id.location -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, MapFragment())
                        .commit()
                    true
                }
                R.id.all -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}

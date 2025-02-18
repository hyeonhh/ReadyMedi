package com.teammeditalk.medicalconnect.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding>(
    private val inflater: (LayoutInflater) -> B,
) : AppCompatActivity() {
    protected lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        onBindLayout()
    }

    protected open fun onBindLayout() = Unit
}

package com.teammeditalk.medicalconnect.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B: ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {

    private var _binding : B?= null
    protected val binding get() = requireNotNull(_binding)

    //todo : abstract  vs open
//    protected open val viewModel : VM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindLayout()
    }

    protected open fun onBindLayout() = Unit

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }



}
package com.teammeditalk.medicalconnect.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teammeditalk.medicalconnect.data.model.search.SearchLocationItem
import com.teammeditalk.medicalconnect.databinding.FragmentMapBottomSheetBinding

class MapInfoBottomSheet(
    private val info: SearchLocationItem,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentMapBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMapBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (info.availableForeignLanguageList.isEmpty()) {
            binding.ivLangAvailable.visibility = View.GONE
        } else {
            View.VISIBLE
        }

        binding.tvName.text = info.name
        binding.tvForeignLanguageAvailable.text =
            if (info.availableForeignLanguageList.isNotEmpty()) {
                info.availableForeignLanguageList
                    .toString()
                    .replace(
                        ",",
                        " ",
                    )
            } else {
                "없음"
            }
        binding.tvAddress.text = info.address
        binding.tvCategory.text = info.categoryName
        binding.tvPhone.text = info.phone
    }
}

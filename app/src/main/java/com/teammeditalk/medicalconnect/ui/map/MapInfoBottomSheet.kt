package com.teammeditalk.medicalconnect.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.data.model.search.SearchLocationItem
import com.teammeditalk.medicalconnect.databinding.FragmentMapBottomSheetBinding
import timber.log.Timber

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

        Timber.d("정보 info :$info")
        if (info.availableForeignLanguageList.isEmpty()) {
            binding.ivLangAvailable.visibility = View.GONE
        } else {
            if (info.type == "Hospital") {
                binding.ivLangAvailable.setImageDrawable(resources.getDrawable(R.drawable.ic_translate_badge))
            } else {
                binding.ivLangAvailable.setImageDrawable(resources.getDrawable(R.drawable.ic_lang_available_phar))
            }
            View.VISIBLE
        }

        if (info.type == "Hospital") {
            binding.imageView12.setBackgroundResource(R.drawable.ic_hospital_25dp)
        } else {
            binding.imageView12.setBackgroundResource(R.drawable.ic_pill)
        }
        binding.tvName.text = info.name
        binding.tvForeignLanguageAvailable.text =
            if (info.availableForeignLanguageList.isNotEmpty()) {
                info.availableForeignLanguageList.joinToString(", ")
            } else {
                "없음"
            }
        binding.tvAddress.text = info.address
//        binding.tvTime.text = info.time.toString()
        binding.tvCategory.text = info.categoryName.replaceBeforeLast(" ", "")
        binding.tvPhone.text = info.phone

        binding.txtInfo.text = info.placeUrl
    }
}

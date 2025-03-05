package com.teammeditalk.medicalconnect.ui.question.joint.result

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentJointSymptomResultBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentJointSymptomResult :
    BaseFragment<FragmentJointSymptomResultBinding>(
        FragmentJointSymptomResultBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.layout.visibility = View.VISIBLE
                binding.layoutHospitalVersion.visibility = View.GONE
            } else {
                binding.layout.visibility = View.GONE
                binding.layoutHospitalVersion.visibility = View.VISIBLE
            }
        }

        binding.layoutUserHealthInfo.viewModel = viewModel
        binding.layoutUserHealthInfo.lifecycleOwner = viewLifecycleOwner

        binding.layoutCurrentSymptom.viewModel = viewModel
        binding.layoutUserHealthInfo.lifecycleOwner = viewLifecycleOwner

        binding.hospitalVersion.symptom.viewModel = viewModel
        binding.hospitalVersion.familyDiseaseAndDrug.viewModel = viewModel
        binding.hospitalVersion.currentSymptom.viewModel = viewModel
        binding.hospitalVersion.injury.viewModel = viewModel
        binding.hospitalVersion.additionalInput.viewModel = viewModel

        binding.hospitalVersion.symptom.lifecycleOwner = viewLifecycleOwner
        binding.hospitalVersion.familyDiseaseAndDrug.lifecycleOwner = viewLifecycleOwner
        binding.hospitalVersion.currentSymptom.lifecycleOwner = viewLifecycleOwner
        binding.hospitalVersion.injury.lifecycleOwner = viewLifecycleOwner
        binding.hospitalVersion.additionalInput.lifecycleOwner = viewLifecycleOwner

        binding.hospitalType.btnGoToMap.setOnClickListener {
            val action = FragmentJointSymptomResultDirections.actionFragmentJointSymptomResultToMapFragment5("정형외과")
            findNavController().navigate(
                action,
            )
        }
        binding.btnBack.setOnClickListener { findNavController().navigate(R.id.jointAdditionalInputFragment) }
        binding.btnClose.setOnClickListener { findNavController().navigate(R.id.homeFragment5) }
        viewModel.saveJointResponse()
    }
}

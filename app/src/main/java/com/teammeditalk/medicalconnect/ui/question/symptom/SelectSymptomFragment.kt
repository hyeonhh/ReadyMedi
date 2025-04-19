package com.teammeditalk.medicalconnect.ui.question.symptom

import android.view.View
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import com.teammeditalk.medicalconnect.ui.util.SelectCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSymptomFragment :
    BaseFragment<FragmentSelectSymptomBinding>(
        FragmentSelectSymptomBinding::inflate,
    ) {
    private val navController by lazy { findNavController() }
    private var selectedLayout: Int = 0
    private var selectedSymptom: String = ""
    private var selectedCategory: String = ""
    private var selectedSymptomByKorean: String = ""
    private var selectedSymptomContentId = ""

    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onBindLayout() {
        super.onBindLayout()

        binding.btnBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnNext.setOnClickListener {
            val name = resources.getResourceEntryName(selectedLayout)

            // todo : else문 안나오도록 하기 !
            val category =
                when (name) {
                    "layout_women" -> "산부인과"
                    "layout_respiratory", "layout_digestive", "layout_fatigue", "layout_allergy", "layout_chronic" -> "내과"
                    "layout_joint", "layout_injury" -> "정형외과"
                    "layout_dental" -> "치과"
                    "layout_breast" -> "일반외과"
                    else -> "내과"
                }
            viewModel.selectSymptom(selectedSymptom = selectedSymptom, selectedCategory = selectedCategory, hospitalCategory = category)

            // todo : 한국어 버전도 저장!
            viewModel.setSymptomByKorean(selectedSymptomByKorean)
            viewModel.setSymptomContentId(selectedSymptomContentId)

            when (category) {
                "치과" -> {
                    navController.navigate(R.id.action_selectSymptomFragment_to_dental_nav2)
                }
                "내과" -> {
                    navController.navigate(R.id.action_selectSymptomFragment_to_inner_nav2)
                }
                "정형외과" -> {
                    navController.navigate(R.id.action_selectSymptomFragment_to_joint_nav)
                }
                "일반외과" -> {
                    navController.navigate(R.id.action_selectSymptomFragment_to_general_nav)
                }
                "산부인과" -> {
                    navController.navigate(R.id.action_selectSymptomFragment_to_woman_nav)
                }
            }
        }

        with(binding.layoutRespiratory) {
            for (child in layoutSymptomCategory.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = it.getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutSymptomCategory.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutDigestive) {
            for (child in layoutDigestive.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutDigestive.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutFatigue) {
            // 자식들 클릭 시 selected 설정
            for (child in layoutFatigue.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutFatigue.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutAllergy) {
            for (child in layoutAllergy.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutAllergy.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutChronic) {
            for (child in layoutChronic.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutChronic.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutJoint) {
            for (child in layoutJoint.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutJoint.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
        with(binding.layoutInjury) {
            for (child in layoutInjury.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutInjury.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }

        with(binding.layoutDental) {
            for (child in layoutDental.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutDental.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
        with(binding.layoutBreast) {
            for (child in layoutBreast.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second
                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutBreast.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
        with(binding.layoutWomen) {
            for (child in layoutWomen.children) {
                child.setOnClickListener {
                    initSelectedSymptom()
                    it.isSelected = !it.isSelected
                    if (it.isSelected) selectedLayout = (it.parent as View).id
                    selectedSymptom = (it as SelectCategory).getContent().second

                    selectedCategory = (it as SelectCategory).getContent().first
                    selectedSymptomByKorean = it.getKoreanString(it.tag.toString())
                    selectedSymptomContentId = it.tag.toString()
                }
            }
            btnSymptom.setOnClickListener {
                it.isSelected = !it.isSelected
                layoutWomen.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initSelectedSymptom() {
        with(binding) {
            layoutRespiratory.layoutSymptomCategory.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
            layoutDigestive.layoutDigestive.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
            layoutFatigue.layoutFatigue.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
            layoutAllergy.layoutAllergy.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
            layoutChronic.layoutChronic.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }

            layoutJoint.layoutJoint.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
            layoutInjury.layoutInjury.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
            layoutDental.layoutDental.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }

            layoutBreast.layoutBreast.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }

            layoutWomen.layoutWomen.apply {
                for (child in this.children) {
                    child.isSelected = false
                }
            }
        }
    }
}

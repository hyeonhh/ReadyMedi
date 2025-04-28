package com.teammeditalk.medicalconnect.ui.question.common.region

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.teammeditalk.medicalconnect.R
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSelectSymptomRegionBinding
import com.teammeditalk.medicalconnect.ui.question.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SelectSymptomRegionFragment :
    BaseFragment<FragmentSelectSymptomRegionBinding>(
        FragmentSelectSymptomRegionBinding::inflate,
    ) {
    private val viewModel: QuestionViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
    private val args by navArgs<SelectSymptomRegionFragmentArgs>()
    private var isChecked: Boolean = false

    private var selectedBodyPart = ""
    private var regionByKorean = ""

    override fun onBindLayout() {
        super.onBindLayout()

        // 잘 모를 경우 체크박스
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            this.isChecked = isChecked
        }

        binding.ivImage.setImageDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_front))
        context?.getColor(R.color.white)?.let { it1 -> binding.tvFront.setTextColor(it1) }

        binding.tvFront.isSelected = true
        binding.tvBack.isSelected = false

        binding.tvFront.setOnClickListener {
            it.isSelected = true
            binding.tvBack.isSelected = false
            context?.getColor(R.color.white)?.let { it1 -> binding.tvFront.setTextColor(it1) }
            context?.getColor(R.color.gray90)?.let { it1 -> binding.tvBack.setTextColor(it1) }

            binding.ivImage.setImageDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_front))
        }

        binding.tvBack.setOnClickListener {
            it.isSelected = true
            binding.tvFront.isSelected = false
            context?.getColor(R.color.white)?.let { it1 -> binding.tvBack.setTextColor(it1) }
            context?.getColor(R.color.gray90)?.let { it1 -> binding.tvFront.setTextColor(it1) }

            binding.ivImage.setImageDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_region_back))
        }

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnNext.setOnClickListener {
            if (selectedBodyPart == "" && !isChecked) {
                binding.warn.layoutLanguageWarn.visibility = View.VISIBLE
            } else {
                if (isChecked) {
                    binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                    viewModel.setRegion(resources.getString(R.string.not_sure))
                    viewModel.setSymptomRegionByKorean("잘 모르겠어요")
                } else {
                    binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE
                    viewModel.setRegion(selectedBodyPart)
                    viewModel.setSymptomRegionByKorean(regionByKorean)
                }
                when (args.hospitalType) {
                    "내과" -> {
                        val bundle = bundleOf("hospital_type" to "내과")
                        navController.navigate(R.id.selectInnerStartFragment, bundle)
                    }
                    "일반" -> {
                        val bundle = bundleOf("hospital_type" to "일반")
                        navController.navigate(R.id.selectGeneralSymptomStartFragment, bundle)
                    }
                    "정형" -> {
                        val bundle = bundleOf("hospital_type" to "정형")
                        navController.navigate(R.id.selectJointSymptomStartFragment, bundle)
                    }
                }
            }
        }
        setupBodyPartRegions()
    }

    /**
     * 신체 부위 영역을 정의하는 데이터 클래스
     */
    data class BodyPartRegion(
        val x1: Float,
        val y1: Float, // 좌상단 좌표 (0~1)
        val x2: Float,
        val y2: Float, // 우하단 좌표 (0~1)
        val name: String, // SVG에 정의된 부위 이름
    )

    /**
     * 신체 부위 처리 함수
     */
    private fun handleBodyPartSelection(bodyPart: String) {
        // 선택된 부위 이름을 한글로 변환
        regionByKorean = getKoreanBodyPartName(bodyPart)

        updatePathColorWithReflection(bodyPart)

        when (bodyPart) {
            "face" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_face,
                    ),
                )
            }
            "neck" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_neck,
                    ),
                )
            }
            "left_head" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_head_left,
                    ),
                )
            }
            "right_head" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_head_right,
                    ),
                )
            }
            "stomach_left_top" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_stomach_left_top,
                    ),
                )
            }
            "stomach_right_top" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_stomach_right_top,
                    ),
                )
            }
            "breast_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_breast_left,
                    ),
                )
            }
            "breast_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_breast_right,
                    ),
                )
            }
            "stomach_left_bottom" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_stomach_left_bottom,
                    ),
                )
            }
            "stomach_right_bottom" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_stomach_right_bottom,
                    ),
                )
            }
            "left_shoulder" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_shoulder_left,
                    ),
                )
            }
            "shoulder_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_shoulder_right,
                    ),
                )
            }
            "left_arm_top" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_arm_left_top,
                    ),
                )
            }
            "arm_right_top" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_arm_right_top,
                    ),
                )
            }
            "left_arm_bottom" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_arm_left_bottom,
                    ),
                )
            }
            "arm_right_bottom" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_arm_right_bottom,
                    ),
                )
            }
            "left_hand" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_hand_left,
                    ),
                )
            }
            "hand_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_hand_right,
                    ),
                )
            }
            // 다리
            "leg_top_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_leg_top_left,
                    ),
                )
            }
            "leg_top_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_leg_top_right,
                    ),
                )
            }
            "leg_middle_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_leg_middle_right,
                    ),
                )
            }
            "leg_middle_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_leg_middle_left,
                    ),
                )
            }

            "leg_bottom_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_leg_bottom_right,
                    ),
                )
            }

            "leg_bottom_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_leg_bottom_left,
                    ),
                )
            }

            "ankle_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_ankle_right,
                    ),
                )
            }
            "ankle_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_ankle_left,
                    ),
                )
            }
            "foot_front_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_foot_front_right,
                    ),
                )
            }

            "foot_front_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_foot_front_left,
                    ),
                )
            }
            "foot_back_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_foot_back_right,
                    ),
                )
            }
            "foot_back_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_foot_back_left,
                    ),
                )
            }
            // 뒤

            "back_head" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_head),
                )
            }
            "back_neck" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_neck),
                )
            }
            "back" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_body),
                )
            }
            "hip_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_hip_left),
                )
            }
            "hip_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_hip_right),
                )
            }
            "back_top_arm_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_top_arm_left),
                )
            }
            "back_top_arm_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_top_arm_right),
                )
            }
            "back_middle_arm_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_middle_arm_left),
                )
            }
            "back_bottom_arm_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_bottom_arm_left),
                )
            }
            "back_bottom_arm_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_bottom_arm_right),
                )
            }
            "back_middle_arm_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_middle_arm_right),
                )
            }
            "back_hand_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_hand_left),
                )
            }
            "back_hand_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_hand_right),
                )
            }
            "back_top_leg_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_top_back_leg_left),
                )
            }
            "back_top_leg_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_top_back_leg_right),
                )
            }
            "back_bottom_leg_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_bottom_leg_left),
                )
            }
            "back_bottom_leg_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_bottom_leg_right),
                )
            }
            "back_foot_left" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_foot_left),
                )
            }
            "back_foot_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_back_foot_right),
                )
            }
        }
    }

    /**
     * 영어 부위 이름을 한글로 변환
     */
    private fun getKoreanBodyPartName(pathName: String): String =
        when (pathName) {
            // 앞면 신체 부위
            "face" -> "얼굴"
            "left_head" -> "머리 왼쪽"
            "right_head" -> "머리 오른쪽"
            "neck" -> "목"
            "stomach_left_top" -> "상복부 왼쪽"
            "stomach_right_top" -> "상복부 오른쪽"
            "breast_left" -> "가슴 왼쪽"
            "breast_right" -> "가슴 오른쪽"
            "stomach_left_bottom" -> "하복부 왼쪽"
            "stomach_right_bottom" -> "하복부 오른쪽"
            "left_shoulder" -> "어깨 왼쪽"
            "shoulder_right" -> "어깨 오른쪽"
            "left_arm_top" -> "팔 윗부분 왼쪽"
            "arm_right_top" -> "팔 윗부분 오른쪽"
            "left_arm_bottom" -> "팔 아랫부분 왼쪽"
            "arm_right_bottom" -> "팔 아랫부분 오른쪽"
            "left_hand" -> "손 왼쪽"
            "hand_right" -> "손 오른쪽"

            // 다리 부분 추가
            "leg_top_left" -> "왼쪽 다리 상부"
            "leg_middle_left" -> "왼쪽 다리 중부"
            "leg_bottom_left" -> "왼쪽 다리 하부"
            "ankle_left" -> "왼쪽 발목"
            "foot_front_left" -> "왼쪽 발 앞"
            "foot_front_right" -> "오른쪽 발 앞"
            "foot_back_right" -> "오른쪽 발 뒤"
            "foot_back_left" -> "왼쪽 발 뒤"

            "leg_top_right" -> "오른쪽 다리 상부"
            "leg_middle_right" -> "오른쪽 다리 중부"
            "leg_bottom_right" -> "오른쪽 다리 하부"
            "ankle_right" -> "오른쪽 발목"

            // 뒷면 신체 부위 (추가)
            "back_head" -> "뒷머리"
            "back_neck" -> "뒷목"
            "back" -> "등"
            "hip_left" -> "왼쪽 엉덩이"
            "hip_right" -> "오른쪽 엉덩이"
            "back_top_arm_left" -> "왼쪽 팔 상부(뒤)"
            "back_top_arm_right" -> "오른쪽 팔 상부(뒤)"
            "back_middle_arm_left" -> "왼쪽 팔 중부(뒤)"
            "back_middle_arm_right" -> "오른쪽 팔 중부(뒤)"
            "back_bottom_arm_left" -> "왼쪽 팔 하부(뒤)"
            "back_bottom_arm_right" -> "오른쪽 팔 하부(뒤)"
            "back_hand_left" -> "왼쪽 손(뒤)"
            "back_hand_right" -> "오른쪽 손(뒤)"
            "back_top_leg_left" -> "왼쪽 다리 상부(뒤)"
            "back_top_leg_right" -> "오른쪽 다리 상부(뒤)"
            "back_bottom_leg_left" -> "왼쪽 다리 하부(뒤)"
            "back_bottom_leg_right" -> "오른쪽 다리 하부(뒤)"
            "back_foot_left" -> "왼쪽 발(뒤)"
            "back_foot_right" -> "오른쪽 발(뒤)"

            else -> pathName.replace("_", " ")
        }

    private fun updatePathColorWithReflection(pathName: String) {
        try {
            val drawable = binding.ivImage.drawable as? VectorDrawableCompat ?: return

            Timber.d("drawable :$drawable")
            // getTargetByName 메서드 찾기
            val getTargetByNameMethod =
                drawable.javaClass.getDeclaredMethod(
                    "getTargetByName",
                    String::class.java,
                )
            getTargetByNameMethod.isAccessible = true

            // 지정된 이름의 타겟(경로) 가져오기
            val path = getTargetByNameMethod.invoke(drawable, pathName)

            // fillColor 필드 찾기
            val fillColorField = path?.javaClass?.getDeclaredField("fillColor")
            fillColorField?.isAccessible = true

            // fillColor 값 변경
            fillColorField?.set(path, ColorStateList.valueOf(resources.getColor(R.color.blue30, null)))

            // 변경사항 적용을 위해 이미지뷰 갱신
            binding.ivImage.invalidate()
        } catch (e: Exception) {
            Timber.e(e, "색상 변경 중 오류 발생")
        }
    }

    /**
     * 신체 부위 영역을 정의하고 터치 감지 설정
     * SVG 파일 기반으로 각 부위의 대략적인 위치를 매핑합니다.
     */

    @SuppressLint("ClickableViewAccessibility")
    private fun setupBodyPartRegions() {
        // 앞면 신체 부위 영역 정의 (기존 코드 유지)
        val frontBodyPartRegions =
            listOf(
                // 머리 부분
                BodyPartRegion(0.4f, 0.0f, 0.6f, 0.12f, "face"), // 얼굴
                BodyPartRegion(0.2f, 0.0f, 0.4f, 0.12f, "left_head"), // 머리 왼쪽
                BodyPartRegion(0.6f, 0.0f, 0.8f, 0.12f, "right_head"), // 머리 오른쪽
                // 목
                BodyPartRegion(0.4f, 0.12f, 0.6f, 0.18f, "neck"), // 목
                // 상체
                BodyPartRegion(0.25f, 0.18f, 0.45f, 0.3f, "breast_left"), // 가슴 왼쪽
                BodyPartRegion(0.55f, 0.18f, 0.75f, 0.3f, "breast_right"), // 가슴 오른쪽
                BodyPartRegion(0.25f, 0.3f, 0.45f, 0.38f, "stomach_left_top"), // 상복부 왼쪽
                BodyPartRegion(0.55f, 0.3f, 0.75f, 0.38f, "stomach_right_top"), // 상복부 오른쪽
                BodyPartRegion(0.25f, 0.38f, 0.43f, 0.41f, "stomach_left_bottom"), // 하복부 왼쪽
                BodyPartRegion(0.55f, 0.38f, 0.75f, 0.5f, "stomach_right_bottom"), // 하복부 오른쪽
                // 어깨
                BodyPartRegion(0.1f, 0.18f, 0.25f, 0.3f, "left_shoulder"), // 어깨 왼쪽
                BodyPartRegion(0.75f, 0.18f, 0.9f, 0.3f, "shoulder_right"), // 어깨 오른쪽
                // 팔 - 개선된 영역 정의
                BodyPartRegion(0.01f, 0.22f, 0.15f, 0.33f, "left_arm_top"), // 팔 왼쪽 상부 (어깨~팔꿈치)
                BodyPartRegion(0.01f, 0.33f, 0.15f, 0.48f, "left_arm_bottom"), // 팔 왼쪽 하부 (팔꿈치~손목)
                BodyPartRegion(0.85f, 0.22f, 0.99f, 0.33f, "arm_right_top"), // 팔 오른쪽 상부 (어깨~팔꿈치)
                BodyPartRegion(0.85f, 0.33f, 0.99f, 0.48f, "arm_right_bottom"), // 팔 오른쪽 하부 (팔꿈치~손목)
                // 손
                BodyPartRegion(0.01f, 0.48f, 0.15f, 0.65f, "left_hand"),
                BodyPartRegion(0.85f, 0.48f, 0.99f, 0.65f, "hand_right"),
                // 왼쪽 다리 부분
                BodyPartRegion(0.29f, 0.42f, 0.45f, 0.55f, "leg_top_left"), // 왼쪽 다리 상부
                BodyPartRegion(0.29f, 0.55f, 0.45f, 0.75f, "leg_middle_left"), // 왼쪽 다리 중간
                BodyPartRegion(0.29f, 0.75f, 0.45f, 0.89f, "leg_bottom_left"), // 왼쪽 다리 하부
                BodyPartRegion(0.29f, 0.89f, 0.45f, 0.93f, "ankle_left"), // 왼쪽 발목
                // 오른쪽 다리 부분
                BodyPartRegion(0.55f, 0.42f, 0.71f, 0.55f, "leg_top_right"), // 오른쪽 다리 상부
                BodyPartRegion(0.55f, 0.55f, 0.71f, 0.75f, "leg_middle_right"), // 오른쪽 다리 중간
                BodyPartRegion(0.55f, 0.75f, 0.71f, 0.89f, "leg_bottom_right"), // 오른쪽 다리 하부
                BodyPartRegion(0.55f, 0.89f, 0.71f, 0.93f, "ankle_right"), // 오른쪽 발목
                // 발
                BodyPartRegion(0.18f, 0.94f, 0.25f, 0.99f, "foot_front_left"), // 왼쪽 발 앞부분
                BodyPartRegion(0.19f, 0.94f, 0.33f, 0.99f, "foot_back_left"), // 왼쪽 발 뒷부분
                BodyPartRegion(0.63f, 0.94f, 0.70f, 0.99f, "foot_front_right"), // 오른쪽 발 앞부분
                BodyPartRegion(0.71f, 0.94f, 0.79f, 0.99f, "foot_back_right"), // 오른쪽 발 뒷부분
            )

        // 뒷면 신체 부위 영역 정의 (SVG 파일에서 분석한 위치)
        val backBodyPartRegions =
            listOf(
                // 머리 부분
                BodyPartRegion(0.4f, 0.0f, 0.6f, 0.12f, "back_head"), // 뒷머리
                // 목
                BodyPartRegion(0.4f, 0.12f, 0.6f, 0.18f, "back_neck"), // 뒷목
                // 상체
                BodyPartRegion(0.3f, 0.18f, 0.7f, 0.42f, "back"),
                // 엉덩이
                BodyPartRegion(0.29f, 0.42f, 0.45f, 0.53f, "hip_left"), // 엉덩이 왼쪽
                BodyPartRegion(0.55f, 0.42f, 0.71f, 0.53f, "hip_right"), // 엉덩이 오른쪽
                // 팔 - 이름을 요청한 형식으로 수정
                BodyPartRegion(0.15f, 0.18f, 0.32f, 0.28f, "back_top_arm_left"), // 왼쪽 팔 상부
                BodyPartRegion(0.68f, 0.18f, 0.85f, 0.28f, "back_top_arm_right"), // 오른쪽 팔 상부
                BodyPartRegion(0.08f, 0.28f, 0.25f, 0.38f, "back_middle_arm_left"), // 왼쪽 팔 중간
                BodyPartRegion(0.75f, 0.28f, 0.92f, 0.38f, "back_middle_arm_right"), // 오른쪽 팔 중간
                BodyPartRegion(0.06f, 0.38f, 0.20f, 0.52f, "back_bottom_arm_left"), // 왼쪽 팔 하부
                BodyPartRegion(0.80f, 0.38f, 0.94f, 0.52f, "back_bottom_arm_right"), // 오른쪽 팔 하부
                // 손
                BodyPartRegion(0.02f, 0.52f, 0.18f, 0.62f, "back_hand_left"), // 왼쪽 손
                BodyPartRegion(0.82f, 0.52f, 0.98f, 0.62f, "back_hand_right"), // 오른쪽 손
                // Legs - adjusted to match the SVG path names
                BodyPartRegion(0.3f, 0.53f, 0.48f, 0.7f, "back_top_leg_left"),
                BodyPartRegion(0.52f, 0.53f, 0.7f, 0.7f, "back_top_leg_right"),
                BodyPartRegion(0.3f, 0.7f, 0.48f, 0.85f, "back_bottom_leg_left"),
                BodyPartRegion(0.52f, 0.7f, 0.7f, 0.85f, "back_bottom_leg_right"),
                // feet
                BodyPartRegion(0.25f, 0.93f, 0.45f, 1.0f, "back_foot_left"),
                BodyPartRegion(0.55f, 0.93f, 0.75f, 1.0f, "back_foot_right"),
            )

        // 터치 리스너 설정
        binding.ivImage.setOnTouchListener { view, event ->
            binding.warn.layoutLanguageWarn.visibility = View.INVISIBLE

            if (event.action == MotionEvent.ACTION_DOWN) {
                // 터치 좌표를 0~1 범위로 정규화
                val normalizedX = event.x / view.width
                val normalizedY = event.y / view.height

                Timber.d("normalized x , y :$normalizedX , $normalizedY")

                // 현재 보이는 이미지에 따라 적절한 영역 목록 선택
                val regions = if (binding.tvFront.isSelected) frontBodyPartRegions else backBodyPartRegions

                // 어떤 영역이 터치되었는지 확인
                val touchedRegion =
                    regions.find { region ->
                        normalizedX >= region.x1 &&
                            normalizedX <= region.x2 &&
                            normalizedY >= region.y1 &&
                            normalizedY <= region.y2
                    }

                if (touchedRegion != null) {
                    // 선택된 신체 부위 처리
                    selectedBodyPart = touchedRegion.name
                    handleBodyPartSelection(touchedRegion.name)
                    Timber.d("터치 x=${touchedRegion.x1}, ${touchedRegion.x2} y=${touchedRegion.y1} ${touchedRegion.y2}")
                    return@setOnTouchListener true
                } else {
                    // 디버깅용 로깅 - 감지되지 않은 터치 좌표
                    Timber.d("감지되지 않은 터치: x=$normalizedX, y=$normalizedY")
                }
            }
            false
        }
    }
}

package com.teammeditalk.medicalconnect.ui.question.dental.fragment.region

import android.content.res.ColorStateList
import android.view.MotionEvent
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

    private lateinit var selectedBodyPart: String

    override fun onBindLayout() {
        super.onBindLayout()

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
            viewModel.setRegion(selectedBodyPart)
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
        setupBodyPartRegions()
    }

    /**
     * 신체 부위 영역을 정의하고 터치 감지 설정
     * SVG 파일 기반으로 각 부위의 대략적인 위치를 매핑합니다.
     */
    private fun setupBodyPartRegions() {
        // SVG 분석 결과를 바탕으로 한 신체 부위 영역 정의
        // 각 영역은 (x1, y1, x2, y2) 좌표와 부위 이름으로 구성됩니다.
        // 좌표는 0~1 범위로 정규화되어 있습니다 (화면 크기에 상관없이 동작)
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
                BodyPartRegion(0.25f, 0.38f, 0.45f, 0.5f, "stomach_left_bottom"), // 하복부 왼쪽
                BodyPartRegion(0.55f, 0.38f, 0.75f, 0.5f, "stomach_right_bottom"), // 하복부 오른쪽
                // 어깨
                BodyPartRegion(0.1f, 0.18f, 0.25f, 0.3f, "left_shoulder"), // 어깨 왼쪽
                BodyPartRegion(0.75f, 0.18f, 0.9f, 0.3f, "shoulder_right"), // 어깨 오른쪽
                // 팔 - 개선된 영역 정의
                // 왼쪽 팔 (SVG 기반으로 조정)
                // 수정된 정의
                BodyPartRegion(0.01f, 0.22f, 0.15f, 0.33f, "left_arm_top"), // 팔 왼쪽 상부 (어깨~팔꿈치)
                BodyPartRegion(0.01f, 0.33f, 0.15f, 0.48f, "left_arm_bottom"), // 팔 왼쪽 하부 (팔꿈치~손목)
                // 오른쪽 팔 (SVG 기반으로 조정)
                BodyPartRegion(0.85f, 0.22f, 0.99f, 0.33f, "arm_right_top"), // 팔 오른쪽 상부 (어깨~팔꿈치)
                BodyPartRegion(0.85f, 0.33f, 0.99f, 0.48f, "arm_right_bottom"), // 팔 오른쪽 하부 (팔꿈치~손목)
                // 손
                BodyPartRegion(0.01f, 0.48f, 0.15f, 0.65f, "left_hand"),
                BodyPartRegion(0.85f, 0.48f, 0.99f, 0.65f, "hand_right"),
                // 다리
                BodyPartRegion(0.25f, 0.5f, 0.45f, 0.95f, "left_leg"), // 다리 왼쪽
                BodyPartRegion(0.55f, 0.5f, 0.75f, 0.95f, "leg_right"), // 다리 오른쪽
            )

//        // 뒷면 신체 부위 영역 (필요시 추가)
//        val backBodyPartRegions = listOf(
//            // 여기에 뒷면 신체 부위 영역 정의
//            // ...
//        )

        // 터치 리스너 설정
        binding.ivImage.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // 터치 좌표를 0~1 범위로 정규화
                val normalizedX = event.x / view.width
                val normalizedY = event.y / view.height

                // 현재 보이는 이미지에 따라 적절한 영역 목록 선택
                val regions = if (binding.tvFront.isSelected) frontBodyPartRegions else frontBodyPartRegions

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
     * 선택된 신체 부위 처리 함수
     */
    private fun handleBodyPartSelection(bodyPart: String) {
        // 선택된 부위 이름을 한글로 변환
        val koreanName = getKoreanBodyPartName(bodyPart)

        // 로그 출력
        Timber.d("선택된 신체 부위: $bodyPart ($koreanName)")
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
                        R.drawable.ic_front,
                    ),
                )
            }
            "shoulder_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front_face,
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
            "left_leg" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front,
                    ),
                )
            }
            "leg_right" -> {
                binding.ivImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_front,
                    ),
                )
            }
        }
    }

    /**
     * 영어 부위 이름을 한글로 변환
     */
    private fun getKoreanBodyPartName(pathName: String): String =
        when (pathName) {
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
            "left_leg" -> "다리 왼쪽"
            "leg_right" -> "다리 오른쪽"
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
}

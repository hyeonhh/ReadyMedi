package com.teammeditalk.medicalconnect.ui

import androidx.lifecycle.lifecycleScope
import com.google.firebase.vertexai.type.Schema
import com.teammeditalk.medicalconnect.base.BaseFragment
import com.teammeditalk.medicalconnect.databinding.FragmentSypmtomResultBinding
import kotlinx.coroutines.launch

class SymptomResultFragment : BaseFragment<FragmentSypmtomResultBinding>(
    FragmentSypmtomResultBinding::inflate
){

    override fun onBindLayout() {
        super.onBindLayout()

        lifecycleScope.launch {
//            val generativeModel = Firebase.vertexAI.generativeModel(
//                "gemini-2.0-flash",
//                generationConfig = generationConfig {
//                    responseMimeType = "application/json"
//                    responseSchema =jsonSchema
//                }
//
//            )

            // Provide a prompt that contains text
            val prompt = "symptom : 신경성 위염, symptom_type : 바늘로 찌르듯 아파요. symptom_degree : 3, duration : 2일 전, when : 식전 ,frequency : 매일, drug : 없음 ,disease : 고지혈증 , allergy : 견과류 , etc : 갑상선 저하를 앓은 경험이 있어요.  -> 당신은 병원에서 근무하고 있는 통역사 입니다. 당신의 역할은 환자들의 증상을 의사들에게 통역하여 전달하는 것입니다.   이 말을 약사 혹은 의사에게 전달할거예요. 이 말을 의료진들이 이해할 수 있도록 의학용어를 사용한 영어로 변경해주세요"

            // To generate text output, call generateContent with the text input
           // val response = generativeModel.generateContent(prompt)
           // print(response.text)

        }
    }
    private val jsonSchema = Schema.array(
        Schema.obj(
            mapOf(
                "symptom" to Schema.string(),
                "symptom_type" to Schema.string(),
                "symptom_degree" to Schema.integer(),
                "duration" to Schema.string(),
                "when" to Schema.string(),
                "frequency" to Schema.string(),
                "drug" to Schema.string(),
                "allergy" to Schema.string(),
                "disease" to Schema.string(),
                "etc" to Schema.string(),
            ),
        )
    )


}
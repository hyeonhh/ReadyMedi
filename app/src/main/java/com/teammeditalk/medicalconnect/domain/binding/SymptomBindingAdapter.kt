package com.teammeditalk.medicalconnect.domain.binding

object SymptomBindingAdapter {
//    @JvmStatic
//    @BindingAdapter("symptomTitle", "useGeneralVM", "generalVM", "questionVM", "lifecycleOwner", requireAll = false)
//    fun setSymptomTitle(
//        textView: TextView,
//        symptomTitle: String?,
//        useGeneralVM: Boolean?,
//        generalVM: GeneralSymptomViewModel?,
//        questionVM: QuestionViewModel?,
//    ) {

//        when {
//            useGeneralVM == true && generalVM != null -> {
//                lifecycleOwner.lifecycleScope.launch {
//                    generalVM.generalResponse.collectLatest {
//                        Timber.d("binding adapter 데이터 :${it.symptomTitle}")
//                        textView.text = it.symptomTitle
//                    }
//                }
//            }
//            questionVM != null -> {
//                lifecycleOwner.lifecycleScope.launch {
//                    questionVM.selectedSymptom.collectLatest {
//                        textView.text = it.first
//                    }
//                }
//            }
//            else -> {
//                textView.text = symptomTitle ?: "테스트"
//            }
//        }
}

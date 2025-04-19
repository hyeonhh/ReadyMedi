package com.teammeditalk.medicalconnect.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.data.serializer.UserAuthPreferencesSerializer.userAuthPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _uid = MutableStateFlow<String?>(null)
        val uid = _uid.asStateFlow()

        init {
            viewModelScope.launch {
                getUid()
            }
        }

        private suspend fun getUid() {
            viewModelScope
                .async {
                    try {
                        context.userAuthPreferencesStore.data.collectLatest {
                            Timber.d("main viewmodel :${it.uid}")
                            _uid.value = it.uid
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        _uid.value = "-1"
                    }
                }.await()
        }
    }

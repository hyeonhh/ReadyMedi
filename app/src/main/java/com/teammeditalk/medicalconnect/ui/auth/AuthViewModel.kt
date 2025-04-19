package com.teammeditalk.medicalconnect.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammeditalk.medicalconnect.data.serializer.UserAuthPreferencesSerializer.userAuthPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        private val _uid = MutableStateFlow<String?>(null)
        val uid = _uid.asStateFlow()

        init {
            getUid()
        }

        private fun getUid() {
            viewModelScope.launch {
                try {
                    context.userAuthPreferencesStore.data.collectLatest {
                        _uid.value = it.uid
                        Timber.d("얻은 uid :${it.uid}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uid.value = "-1"
                }
            }
        }

        suspend fun saveUid(uid: String) {
            context.userAuthPreferencesStore.updateData {
                it
                    .toBuilder()
                    .setUid(uid)
                    .build()
            }
            getUid()
        }

        suspend fun saveProfileUrl(
            url: String,
            nickName: String,
        ) {
            context.userAuthPreferencesStore.updateData {
                it
                    .toBuilder()
                    .setProfile(url)
                    .setNickName(nickName)
                    .build()
            }
        }
    }

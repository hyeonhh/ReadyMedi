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
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        private val _uid = MutableStateFlow("")
        val uid = _uid.asStateFlow()

        init {
            getUid()
        }

        private fun getUid() {
            viewModelScope.launch {
                try {
                    context.userAuthPreferencesStore.data.collectLatest {
                        _uid.value = it.uid
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uid.value = ""
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

package com.teammeditalk.medicalconnect.ui.user

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
class UserViewModel
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _userId = MutableStateFlow("")
        val userId = _userId.asStateFlow()

        private val _profileUri = MutableStateFlow("")
        val profileUri = _profileUri.asStateFlow()

        private val _nickName = MutableStateFlow("")
        val nickName = _nickName.asStateFlow()

        fun deleteUserData() {
            viewModelScope.launch {
                context.userAuthPreferencesStore.updateData {
                    it
                        .toBuilder()
                        .clear()
                        .build()
                }
            }
        }

        init {
            viewModelScope.launch {
                context.userAuthPreferencesStore.data.collectLatest {
                    _userId.value = it.uid
                    _profileUri.value = it.profile
                    _nickName.value = it.nickName
                }
            }
        }
    }

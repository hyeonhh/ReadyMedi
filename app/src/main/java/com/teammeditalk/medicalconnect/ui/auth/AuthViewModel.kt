package com.teammeditalk.medicalconnect.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.teammeditalk.medicalconnect.data.serializer.UserAuthPreferencesSerializer.userAuthPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
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

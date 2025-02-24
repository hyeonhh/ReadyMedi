package com.teammeditalk.medicalconnect.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserPreferencesRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    )

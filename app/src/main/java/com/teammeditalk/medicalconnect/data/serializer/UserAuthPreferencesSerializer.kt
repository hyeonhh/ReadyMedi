package com.teammeditalk.medicalconnect.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.teammeditalk.medicalconnect.UserAuthInfo
import java.io.InputStream
import java.io.OutputStream

object UserAuthPreferencesSerializer : Serializer<UserAuthInfo> {
    override val defaultValue: UserAuthInfo
        get() = UserAuthInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserAuthInfo {
        try {
            return UserAuthInfo.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("cannot read proto ", e)
        }
    }

    override suspend fun writeTo(
        t: UserAuthInfo,
        output: OutputStream,
    ) = t.writeTo(output)

    val Context.userAuthPreferencesStore: DataStore<UserAuthInfo> by dataStore(
        fileName = "user_auth_info.pb",
        serializer = UserAuthPreferencesSerializer,
    )
}

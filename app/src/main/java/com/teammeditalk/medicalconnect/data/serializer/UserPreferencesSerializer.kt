package com.teammeditalk.medicalconnect.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.teammeditalk.medicalconnect.UserHealthInfo
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserHealthInfo> {
    override val defaultValue: UserHealthInfo = UserHealthInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserHealthInfo {
        try {
            return UserHealthInfo.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("cannot read proto ", e)
        }
    }

    override suspend fun writeTo(
        t: UserHealthInfo,
        output: OutputStream,
    ) = t.writeTo(output)
}

package com.github.callmephil1.crypt.data

import org.apache.commons.codec.binary.Base32
import java.nio.ByteBuffer
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and
import kotlin.math.floor

object TOTP {
    fun generateCode(secret: String): String {
        if (secret.isBlank())
            return ""

        val mac = Mac.getInstance("HmacSHA1")
        val decodeSecret = Base32().decode(secret)
        val secretKey = SecretKeySpec(decodeSecret, "RAW")

        mac.init(secretKey)

        val sinceEpoch = System.currentTimeMillis() / 1000
        val step = floor(sinceEpoch / 30.0).toLong()

        val epochBuffer = ByteBuffer.allocate(8).putLong(step)

        val hmacBytes = mac.doFinal(epochBuffer.array())

        val buffer = ByteBuffer.wrap(hmacBytes)
        val offset = (buffer.get(19) and 0x0F).toInt()
        val totpBytes = buffer.getInt(offset) and 0x7FFFFFFF
        val otpCode = totpBytes % 1_000_000

        return otpCode.toString().padStart(6, '0')
    }

    fun progressToRefresh(step: Int = 30) = (1.0 - (System.currentTimeMillis() / 1000.0 / 30.0).mod(1.0))

    fun secondsBeforeRefresh(step: Int = 30) = step - ((System.currentTimeMillis() / 1000) % 30)
}
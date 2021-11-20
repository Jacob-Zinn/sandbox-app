package com.example.sandbox.util

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object ShaOne {
    fun hash(hashString: String): String {
        var sha1 = ""

        try {
            val digest = MessageDigest.getInstance("SHA-1")
            digest.reset()
            digest.update(hashString.toByteArray(StandardCharsets.UTF_8))
            sha1 = String.format("%040x", BigInteger(1, digest.digest()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sha1
    }

//    @Throws(
//        UnsupportedEncodingException::class,
//        NoSuchAlgorithmException::class,
//        InvalidKeyException::class
//    )
    fun hmacSha1(value: String, key: String): String {
        val type = "HmacSHA1"
        val secret = SecretKeySpec(key.toByteArray(), type)
        val mac: Mac = Mac.getInstance(type)
        mac.init(secret)
        val bytes: ByteArray = mac.doFinal(value.toByteArray())
        return bytesToHex(bytes)
    }

    private val hexArray = "0123456789abcdef".toCharArray()

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}
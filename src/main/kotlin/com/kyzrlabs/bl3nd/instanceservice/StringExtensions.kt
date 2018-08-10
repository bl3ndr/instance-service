package com.kyzrlabs.bl3nd.instanceservice

import java.security.MessageDigest
import java.security.SecureRandom

fun String.Companion.generateHash(size: Int = 12): String {
    val sr = SecureRandom()
    val bytes = ByteArray(20)
    sr.nextBytes(bytes)

    val digest = MessageDigest.getInstance("SHA-256")
    var hash = digest.digest(bytes)

    val from = sr.nextInt(9)

    var hashSize: Int = (size / 2)
    hash = hash.copyOfRange(from, from + hashSize)

    val hexString = StringBuffer()
    for (i in hash.indices) {
        val hex = Integer.toHexString(0xff and hash[i].toInt())
        if (hex.length == 1) hexString.append('0')
        hexString.append(hex)
    }
    return hexString.toString()
}
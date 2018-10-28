package com.kyzrlabs.bl3ndr.instanceservice

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

fun String.hexStringToByteArray(): ByteArray {
    val len = this.length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}

private val HEX_CHARS = "0123456789ABCDEF".toCharArray()
fun ByteArray.toHex() : String{
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}
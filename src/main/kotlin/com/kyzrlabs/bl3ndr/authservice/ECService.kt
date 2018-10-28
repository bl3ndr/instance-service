package com.kyzrlabs.bl3ndr.authservice

import com.kyzrlabs.bl3ndr.instanceservice.hexStringToByteArray
import com.kyzrlabs.bl3ndr.instanceservice.toHex
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.security.spec.ECGenParameterSpec
import javax.crypto.KeyAgreement
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECPrivateKeySpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.math.ec.ECPoint
import java.math.BigInteger
import java.security.*
import java.util.*

@Service("ecService")
@Component
class ECService {

    private val privateKey = "303E020100301006072A8648CE3D020106052B8104000A0427302502010104209608AE8361A6BD965BE93F29505145DDECF63F3BA3B052901A331B16E3CD19EA"
    private final val algorithm = "ECDH"
    private final val curveName = "secp256k1"
    private final val provider = "BC"

    data class LightKeyPair(val publicKey: String, val privateKey: String)
    fun generateKeyPair(): LightKeyPair {
        Security.addProvider(BouncyCastleProvider())

        val keyGen = KeyPairGenerator.getInstance(algorithm, provider)
        keyGen.initialize(ECGenParameterSpec(curveName), SecureRandom())

        val keyPair = keyGen.generateKeyPair()

        val private = privateToByte(keyPair.private)
        val public = publicToByte(keyPair.public)

        return LightKeyPair(public.toHex(), private.toHex());
    }

    fun exchangeDH(privateKeyString: String, publicKeyString: String): String{
        val pubByte = publicKeyString.hexStringToByteArray()
        val privByte = privateKeyString.hexStringToByteArray()

        Security.addProvider(BouncyCastleProvider())

        val ka = KeyAgreement.getInstance(algorithm, provider)
        val params = ECNamedCurveTable.getParameterSpec(curveName)
        val kf = KeyFactory.getInstance(algorithm, provider)

        val prvKeySpec = ECPrivateKeySpec(BigInteger(privByte), params)
        val privateKey = kf.generatePrivate(prvKeySpec)

        val pubKeySpec = ECPublicKeySpec(
                params.curve.decodePoint(pubByte), params)
        val publicKey = kf.generatePublic(pubKeySpec)

        ka.init(privateKey)
        ka.doPhase(publicKey, true)
        val secret = ka.generateSecret()

        return secret.toHex()
    }

    fun generateNewServerPublicKey(): ECPoint {
        val spec = ECNamedCurveTable.getParameterSpec(curveName)

        val bytes = privateKey.hexStringToByteArray()
        val newPK = spec.g.multiply(BigInteger(bytes)).multiply(BigInteger.valueOf(Random().nextLong()))

        return newPK
    }




    @Throws(Exception::class)
    private fun publicToByte(key: PublicKey): ByteArray {
        val eckey = key as org.bouncycastle.jce.interfaces.ECPublicKey
        return eckey.q.encoded
    }

    @Throws(Exception::class)
    private fun privateToByte(key: PrivateKey): ByteArray {
        val eckey = key as org.bouncycastle.jce.interfaces.ECPrivateKey
        return eckey.d.toByteArray()
    }

}
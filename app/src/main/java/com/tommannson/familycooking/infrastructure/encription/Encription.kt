package com.tommannson.familycooking.infrastructure.encription

import android.app.Activity
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PublicKey
import javax.crypto.Cipher

fun Activity.cryptography(){
    val keys = generateKeyPair()!!
    val data = "TEST";

    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
    cipher.init(Cipher.ENCRYPT_MODE, keys.public)

    val savedValue = try {
        this.openFileInput("encrypted").use {
            it.readAllBytes()
        }
    } catch (ex: Exception) {
        null
    }

    val decryptedText = if (savedValue == null) {
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        val encryptedBytes2 = cipher.doFinal(data.toByteArray())
        encryptedBytes2.toString()

        this.openFileOutput("encrypted", Context.MODE_PRIVATE).use {
            it.write(encryptedBytes)
        }

        val decipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        decipher.init(Cipher.DECRYPT_MODE, keys.private)
        val decryptedBytes = decipher.doFinal(encryptedBytes)
        String(decryptedBytes)
    } else {
        val decipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        decipher.init(Cipher.DECRYPT_MODE, keys.private)
        val decryptedBytes = decipher.doFinal(savedValue)
        String(decryptedBytes)
    }
    val asd = decryptedText.toString()
    asd.toString()
}


fun generateKeyPair(): KeyPair? {
    val ks = KeyStore.getInstance("AndroidKeyStore")
    ks.load(null)

    val privateKeyEntry = ks.getEntry("myKeyAlias", null) as KeyStore.PrivateKeyEntry?
    if (privateKeyEntry != null) {
        val privateKey = privateKeyEntry.privateKey
        val publicKey: PublicKey = ks.getCertificate("myKeyAlias").getPublicKey()
        return KeyPair(publicKey, privateKey)
    }

    val keyPairGenerator = KeyPairGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore"
    )

    val keyGenParameterSpec = KeyGenParameterSpec.Builder(
        "myKeyAlias",
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
        .build()

    keyPairGenerator.initialize(keyGenParameterSpec)
    return keyPairGenerator.generateKeyPair()
}
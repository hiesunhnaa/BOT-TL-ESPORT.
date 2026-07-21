package com.tlesport.keygen

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec
import java.util.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("KeyEncryption")

object KeyEncryption {
    private const val ALGORITHM = "AES"
    private const val KEY_SIZE = 256
    
    fun encrypt(data: String, secretKey: String): String {
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)
            val key = generateKey(secretKey)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedData = cipher.doFinal(data.toByteArray())
            Base64.getEncoder().encodeToString(encryptedData)
        } catch (e: Exception) {
            logger.error("Encryption error", e)
            ""
        }
    }
    
    fun decrypt(encryptedData: String, secretKey: String): String {
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)
            val key = generateKey(secretKey)
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decodedData = Base64.getDecoder().decode(encryptedData)
            val decryptedData = cipher.doFinal(decodedData)
            String(decryptedData)
        } catch (e: Exception) {
            logger.error("Decryption error", e)
            ""
        }
    }
    
    private fun generateKey(secretKey: String): SecretKeySpec {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(KEY_SIZE)
        val decodedKey = secretKey.padEnd(32, '0').substring(0, 32).toByteArray()
        return SecretKeySpec(decodedKey, 0, decodedKey.size, ALGORITHM)
    }
}

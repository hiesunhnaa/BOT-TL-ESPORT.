package com.tlesport.keygen

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("KeyValidator")

class KeyValidator(private val secretKey: String) {
    fun validate(key: String): Boolean {
        return try {
            val decrypted = KeyEncryption.decrypt(key, secretKey)
            val parts = decrypted.split("-")
            if (parts.size >= 2) {
                val timestamp = parts[0].toLong()
                val age = System.currentTimeMillis() - timestamp
                age < 365 * 24 * 60 * 60 * 1000 // 1 year expiry
            } else {
                false
            }
        } catch (e: Exception) {
            logger.error("Key validation error", e)
            false
        }
    }
    
    fun isExpired(key: String): Boolean {
        return !validate(key)
    }
}

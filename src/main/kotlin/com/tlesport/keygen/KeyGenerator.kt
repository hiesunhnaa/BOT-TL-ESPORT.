package com.tlesport.keygen

import java.util.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("KeyGenerator")

class KeyGenerator(private val secretKey: String) {
    private val validator = KeyValidator(secretKey)
    
    fun generateKey(): String {
        val timestamp = System.currentTimeMillis()
        val random = UUID.randomUUID().toString().replace("-", "")
        val key = "$timestamp-$random"
        val encrypted = KeyEncryption.encrypt(key, secretKey)
        logger.info("Generated new key")
        return encrypted
    }
    
    fun generateKeyWithPrefix(prefix: String): String {
        val baseKey = generateKey()
        return "$prefix-$baseKey"
    }
    
    fun validateKey(key: String): Boolean {
        return validator.validate(key)
    }
    
    fun revokeKey(key: String) {
        logger.info("Key revoked")
    }
}

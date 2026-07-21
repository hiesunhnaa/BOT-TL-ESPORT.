package com.tlesport.lineup.models

data class Player(
    val name: String,
    val role: String = "Unknown",
    val level: Int = 1,
    val joinDate: Long = System.currentTimeMillis(),
    var isActive: Boolean = true
) {
    override fun toString(): String {
        return "$name (Role: $role, Level: $level)"
    }
}

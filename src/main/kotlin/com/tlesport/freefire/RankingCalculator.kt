package com.tlesport.freefire

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("RankingCalculator")

class RankingCalculator {
    companion object {
        private const val KILL_POINT = 10
        private const val ASSIST_POINT = 5
        private const val DAMAGE_RATIO = 0.1
        private const val SURVIVAL_BONUS = 2
    }
    
    fun calculatePoints(
        kills: Int,
        assists: Int,
        damage: Int,
        survival: Int = 0
    ): Int {
        val killPoints = kills * KILL_POINT
        val assistPoints = assists * ASSIST_POINT
        val damagePoints = (damage * DAMAGE_RATIO).toInt()
        val survivalPoints = survival * SURVIVAL_BONUS
        
        val totalPoints = killPoints + assistPoints + damagePoints + survivalPoints
        logger.info("Calculated points: $totalPoints (kills: $killPoints, assists: $assistPoints, damage: $damagePoints, survival: $survivalPoints)")
        
        return totalPoints
    }
    
    fun getPlayerStats(playerId: String): PlayerStats {
        // Implement API call to get player stats
        return PlayerStats(
            playerId = playerId,
            kills = 0,
            assists = 0,
            damage = 0,
            rank = "Unranked"
        )
    }
    
    fun getRankTier(points: Int): String {
        return when {
            points >= 5000 -> "Mythic"
            points >= 4000 -> "Legend"
            points >= 3000 -> "Master"
            points >= 2000 -> "Gold"
            points >= 1000 -> "Silver"
            else -> "Bronze"
        }
    }
}

data class PlayerStats(
    val playerId: String,
    val kills: Int,
    val assists: Int,
    val damage: Int,
    val rank: String
)

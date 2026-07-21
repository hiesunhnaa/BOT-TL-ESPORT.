package com.tlesport.freefire

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("FFTools")

class FFTools {
    fun calculateWinRate(wins: Int, total: Int): Double {
        return if (total > 0) (wins.toDouble() / total) * 100 else 0.0
    }
    
    fun calculateKDA(kills: Int, deaths: Int, assists: Int): Double {
        return if (deaths > 0) (kills + assists).toDouble() / deaths else (kills + assists).toDouble()
    }
    
    fun getPlayerTier(points: Int): Tier {
        return when {
            points >= 5000 -> Tier.MYTHIC
            points >= 4000 -> Tier.LEGEND
            points >= 3000 -> Tier.MASTER
            points >= 2000 -> Tier.GOLD
            points >= 1000 -> Tier.SILVER
            else -> Tier.BRONZE
        }
    }
    
    fun estimateRankProgression(currentPoints: Int, pointsPerMatch: Int, targetPoints: Int): Int {
        return if (currentPoints < targetPoints) {
            (targetPoints - currentPoints) / pointsPerMatch
        } else 0
    }
}

enum class Tier {
    BRONZE, SILVER, GOLD, MASTER, LEGEND, MYTHIC
}

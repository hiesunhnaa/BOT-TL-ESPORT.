package com.tlesport.lineup

import com.tlesport.lineup.models.Team
import com.tlesport.lineup.models.Player
import com.tlesport.lineup.database.LineupDB
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("LineupManager")

class LineupManager(private val databaseUrl: String) {
    private val database = LineupDB(databaseUrl)
    private val lineups = mutableMapOf<String, Team>()
    
    init {
        logger.info("LineupManager initialized with database: $databaseUrl")
        loadLineups()
    }
    
    fun addPlayer(teamId: String, playerName: String) {
        val player = Player(name = playerName, joinDate = System.currentTimeMillis())
        val team = lineups.getOrPut(teamId) { Team(id = teamId, players = mutableListOf()) }
        team.players.add(player)
        database.savePlayer(teamId, player)
        logger.info("Player $playerName added to team $teamId")
    }
    
    fun removePlayer(teamId: String, playerName: String) {
        val team = lineups[teamId] ?: return
        team.players.removeAll { it.name == playerName }
        database.deletePlayer(teamId, playerName)
        logger.info("Player $playerName removed from team $teamId")
    }
    
    fun getLineup(teamId: String): Team? {
        return lineups[teamId]
    }
    
    fun getAllLineups(): Map<String, Team> {
        return lineups.toMap()
    }
    
    fun exportLineup(teamId: String): String {
        val team = lineups[teamId] ?: return "Team not found"
        return team.toString()
    }
    
    private fun loadLineups() {
        val allLineups = database.loadAllLineups()
        lineups.putAll(allLineups)
        logger.info("Loaded ${allLineups.size} lineups from database")
    }
    
    fun saveAll() {
        lineups.forEach { (teamId, team) ->
            database.saveTeam(teamId, team)
        }
        logger.info("All lineups saved")
    }
}

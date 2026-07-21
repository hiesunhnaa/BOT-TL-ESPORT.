package com.tlesport.lineup.database

import com.tlesport.lineup.models.Team
import com.tlesport.lineup.models.Player
import java.sql.Connection
import java.sql.DriverManager
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("LineupDB")

class LineupDB(private val databaseUrl: String) {
    private lateinit var connection: Connection
    
    init {
        initDatabase()
    }
    
    private fun initDatabase() {
        try {
            connection = DriverManager.getConnection(databaseUrl)
            createTables()
            logger.info("Database initialized: $databaseUrl")
        } catch (e: Exception) {
            logger.error("Failed to initialize database", e)
        }
    }
    
    private fun createTables() {
        val teamTable = """
            CREATE TABLE IF NOT EXISTS teams (
                id TEXT PRIMARY KEY,
                name TEXT,
                created_date INTEGER
            )
        """.trimIndent()
        
        val playerTable = """
            CREATE TABLE IF NOT EXISTS players (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                team_id TEXT,
                name TEXT,
                role TEXT,
                level INTEGER,
                join_date INTEGER,
                is_active BOOLEAN,
                FOREIGN KEY(team_id) REFERENCES teams(id)
            )
        """.trimIndent()
        
        connection.createStatement().use { statement ->
            statement.execute(teamTable)
            statement.execute(playerTable)
        }
    }
    
    fun saveTeam(teamId: String, team: Team) {
        val sql = "INSERT OR REPLACE INTO teams (id, name, created_date) VALUES (?, ?, ?)"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, team.id)
            stmt.setString(2, team.name)
            stmt.setLong(3, team.createdDate)
            stmt.executeUpdate()
        }
    }
    
    fun savePlayer(teamId: String, player: Player) {
        val sql = """INSERT INTO players (team_id, name, role, level, join_date, is_active) 
                    VALUES (?, ?, ?, ?, ?, ?)"""
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, teamId)
            stmt.setString(2, player.name)
            stmt.setString(3, player.role)
            stmt.setInt(4, player.level)
            stmt.setLong(5, player.joinDate)
            stmt.setBoolean(6, player.isActive)
            stmt.executeUpdate()
        }
    }
    
    fun deletePlayer(teamId: String, playerName: String) {
        val sql = "DELETE FROM players WHERE team_id = ? AND name = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, teamId)
            stmt.setString(2, playerName)
            stmt.executeUpdate()
        }
    }
    
    fun loadAllLineups(): Map<String, Team> {
        val lineups = mutableMapOf<String, Team>()
        val sql = "SELECT * FROM teams"
        
        connection.createStatement().use { statement ->
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                val teamId = resultSet.getString("id")
                val teamName = resultSet.getString("name")
                val createdDate = resultSet.getLong("created_date")
                
                val players = loadPlayersByTeam(teamId)
                lineups[teamId] = Team(teamId, teamName, players.toMutableList(), createdDate)
            }
        }
        
        return lineups
    }
    
    private fun loadPlayersByTeam(teamId: String): List<Player> {
        val players = mutableListOf<Player>()
        val sql = "SELECT * FROM players WHERE team_id = ?"
        
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, teamId)
            val resultSet = stmt.executeQuery()
            while (resultSet.next()) {
                players.add(Player(
                    name = resultSet.getString("name"),
                    role = resultSet.getString("role"),
                    level = resultSet.getInt("level"),
                    joinDate = resultSet.getLong("join_date"),
                    isActive = resultSet.getBoolean("is_active")
                ))
            }
        }
        
        return players
    }
}

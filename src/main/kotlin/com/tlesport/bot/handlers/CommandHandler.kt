package com.tlesport.bot.handlers

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import com.tlesport.bot.BotConfig
import com.tlesport.freefire.RankingCalculator
import com.tlesport.lineup.LineupManager
import com.tlesport.keygen.KeyGenerator
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("CommandHandler")

class CommandHandler(private val bot: Bot, private val config: BotConfig) {
    private val rankingCalculator = RankingCalculator()
    private val lineupManager = LineupManager(config.lineup.databaseUrl)
    private val keyGenerator = KeyGenerator(config.keygen.secretKey)
    
    init {
        GlobalEventChannel.subscribeAlways<GroupMessageEvent> { event ->
            val message = event.message.contentToString()
            if (message.startsWith("/")) {
                handleCommand(event, message)
            }
        }
    }
    
    private suspend fun handleCommand(event: GroupMessageEvent, message: String) {
        val parts = message.split(" ")
        val command = parts[0].substring(1)
        
        try {
            when (command) {
                "ff" -> handleFFCommand(event, parts)
                "lineup" -> handleLineupCommand(event, parts)
                "key" -> handleKeyCommand(event, parts)
                else -> event.group.sendMessage(PlainText("Unknown command: /$command"))
            }
        } catch (e: Exception) {
            logger.error("Error handling command", e)
            event.group.sendMessage(PlainText("Error: ${e.message}"))
        }
    }
    
    private suspend fun handleFFCommand(event: GroupMessageEvent, parts: List<String>) {
        if (parts.size < 2) return
        
        val subCommand = parts[1]
        when (subCommand) {
            "rank" -> {
                val player = parts.getOrNull(2) ?: "Unknown"
                val response = "Getting rank for $player..."
                event.group.sendMessage(PlainText(response))
            }
            "calculate" -> {
                if (parts.size >= 5) {
                    val kills = parts[2].toIntOrNull() ?: 0
                    val assists = parts[3].toIntOrNull() ?: 0
                    val damage = parts[4].toIntOrNull() ?: 0
                    val points = rankingCalculator.calculatePoints(kills, assists, damage)
                    event.group.sendMessage(PlainText("Points: $points"))
                }
            }
        }
    }
    
    private suspend fun handleLineupCommand(event: GroupMessageEvent, parts: List<String>) {
        if (parts.size < 2) return
        
        val subCommand = parts[1]
        when (subCommand) {
            "view" -> {
                val lineup = lineupManager.getLineup(event.group.id.toString())
                event.group.sendMessage(PlainText("Lineup: $lineup"))
            }
            "add" -> {
                val playerName = parts.getOrNull(2) ?: "Unknown"
                lineupManager.addPlayer(event.group.id.toString(), playerName)
                event.group.sendMessage(PlainText("Player $playerName added"))
            }
        }
    }
    
    private suspend fun handleKeyCommand(event: GroupMessageEvent, parts: List<String>) {
        if (parts.size < 2) return
        
        val subCommand = parts[1]
        when (subCommand) {
            "generate" -> {
                val key = keyGenerator.generateKey()
                event.group.sendMessage(PlainText("Key: $key"))
            }
            "validate" -> {
                val key = parts.getOrNull(2) ?: ""
                val valid = keyGenerator.validateKey(key)
                event.group.sendMessage(PlainText("Valid: $valid"))
            }
        }
    }
}

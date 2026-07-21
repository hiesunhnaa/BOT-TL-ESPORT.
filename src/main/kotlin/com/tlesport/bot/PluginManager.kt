package com.tlesport.bot

import net.mamoe.mirai.Bot
import com.tlesport.bot.handlers.CommandHandler
import com.tlesport.bot.handlers.MessageHandler
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("PluginManager")

class PluginManager(val bot: Bot, val config: BotConfig) {
    private val handlers = mutableListOf<Any>()
    
    fun loadAll() {
        logger.info("Loading plugins...")
        
        // Register message handlers
        val messageHandler = MessageHandler(bot, config)
        handlers.add(messageHandler)
        logger.info("✓ Message Handler loaded")
        
        // Register command handlers
        val commandHandler = CommandHandler(bot, config)
        handlers.add(commandHandler)
        logger.info("✓ Command Handler loaded")
        
        logger.info("All plugins loaded successfully")
    }
    
    fun unloadAll() {
        handlers.clear()
        logger.info("All plugins unloaded")
    }
}

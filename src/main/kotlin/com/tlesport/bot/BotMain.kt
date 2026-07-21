package com.tlesport.bot

import net.mamoe.mirai.Bot
import net.mamoe.mirai.login
import net.mamoe.mirai.utils.BotConfiguration
import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("BotMain")

suspend fun main() {
    logger.info("Starting BOT-TL-ESPORT Mirai Bot")
    
    // Load configuration
    val config = BotConfig.loadConfig(File("config.yml"))
    
    // Create and login bot
    val bot = Bot(config.bot.qqId, config.bot.password) {
        fileBasedDeviceInfo()
        protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
    }.login()
    
    logger.info("Bot logged in successfully: ${bot.nick}")
    
    // Initialize plugins
    val pluginManager = PluginManager(bot, config)
    pluginManager.loadAll()
    
    // Start webhook server
    val webhookServer = WebhookServer(config.webhook.port)
    webhookServer.start()
    logger.info("Webhook server started on port ${config.webhook.port}")
    
    // Keep bot running
    bot.join()
}

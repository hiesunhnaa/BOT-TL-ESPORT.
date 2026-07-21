package com.tlesport.bot.handlers

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import com.tlesport.bot.BotConfig
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("MessageHandler")

class MessageHandler(private val bot: Bot, private val config: BotConfig) {
    init {
        GlobalEventChannel.subscribeAlways<GroupMessageEvent> { event ->
            logger.debug("Received message: ${event.message.contentToString()}")
        }
    }
}

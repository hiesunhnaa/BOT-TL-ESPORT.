package com.tlesport.bot

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import com.tlesport.bot.webhook.messengerWebhook
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("WebhookServer")

class WebhookServer(private val port: Int) {
    private var server: ApplicationEngine? = null
    
    fun start() {
        server = embeddedServer(Netty, port = port) {
            routing {
                route("/webhook") {
                    messengerWebhook(
                        verifyToken = System.getenv("VERIFY_TOKEN") ?: "verify_token",
                        messageHandler = { body, entry ->
                            logger.debug("Processing message: $entry")
                        }
                    )
                }
            }
        }.start()
        logger.info("Webhook server started on port $port")
    }
    
    fun stop() {
        server?.stop(1000, 5000)
        logger.info("Webhook server stopped")
    }
}

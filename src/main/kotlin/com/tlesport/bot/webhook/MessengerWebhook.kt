package com.tlesport.bot.webhook

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("MessengerWebhook")

fun Route.messengerWebhook(verifyToken: String, messageHandler: (String, String) -> Unit) {
    get("/messenger") {
        val mode = call.request.queryParameters["hub.mode"]
        val token = call.request.queryParameters["hub.verify_token"]
        val challenge = call.request.queryParameters["hub.challenge"]
        
        if (mode == "subscribe" && token == verifyToken && challenge != null) {
            logger.info("Webhook verified")
            call.respondText(challenge)
        } else {
            logger.warn("Invalid webhook verification attempt")
            call.respond(HttpStatusCode.Forbidden)
        }
    }
    
    post("/messenger") {
        try {
            val body = call.receiveText()
            val json = Json.parseToJsonElement(body).jsonObject
            
            if (json["object"]?.toString() == "\"page\"") {
                val entry = json["entry"]?.toString()
                if (entry != null) {
                    logger.info("Received message from Messenger")
                    messageHandler(body, entry)
                }
            }
            
            call.respond(HttpStatusCode.OK, mapOf("status" to "ok"))
        } catch (e: Exception) {
            logger.error("Error processing webhook", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

data class MessengerMessage(
    val senderId: String,
    val recipientId: String,
    val timestamp: Long,
    val text: String?
)

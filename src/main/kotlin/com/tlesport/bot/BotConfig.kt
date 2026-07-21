package com.tlesport.bot

import java.io.File

data class BotConfig(
    val bot: BotSettings,
    val webhook: WebhookSettings,
    val freefire: FreeFireSettings,
    val lineup: LineupSettings,
    val keygen: KeyGenSettings
) {
    companion object {
        fun loadConfig(configFile: File): BotConfig {
            // Load from YAML file
            return BotConfig(
                bot = BotSettings(
                    qqId = System.getenv("BOT_QQ_ID")?.toLong() ?: 0L,
                    password = System.getenv("BOT_PASSWORD") ?: ""
                ),
                webhook = WebhookSettings(
                    messengerToken = System.getenv("MESSENGER_TOKEN") ?: "",
                    verifyToken = System.getenv("VERIFY_TOKEN") ?: "",
                    port = System.getenv("WEBHOOK_PORT")?.toInt() ?: 8080
                ),
                freefire = FreeFireSettings(
                    apiBase = System.getenv("FREEFIRE_API_BASE") ?: "https://api.freefire.com",
                    apiKey = System.getenv("FREEFIRE_API_KEY") ?: ""
                ),
                lineup = LineupSettings(
                    databaseUrl = System.getenv("LINEUP_DB_URL") ?: "jdbc:sqlite:lineup.db"
                ),
                keygen = KeyGenSettings(
                    secretKey = System.getenv("KEYGEN_SECRET") ?: "",
                    algorithm = "AES_256"
                )
            )
        }
    }
}

data class BotSettings(
    val qqId: Long,
    val password: String
)

data class WebhookSettings(
    val messengerToken: String,
    val verifyToken: String,
    val port: Int
)

data class FreeFireSettings(
    val apiBase: String,
    val apiKey: String
)

data class LineupSettings(
    val databaseUrl: String
)

data class KeyGenSettings(
    val secretKey: String,
    val algorithm: String
)

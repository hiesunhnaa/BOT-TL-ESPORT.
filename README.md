# BOT-TL-ESPORT - Mirai Bot

A comprehensive Mirai bot featuring Messenger webhook integration, Free Fire ranking calculator, lineup management, and secure key generation.

## Features

- 🤖 **Mirai Bot Framework**: Built on Mirai for QQ bot development
- 💬 **Messenger Webhook**: Integrated webhook for Messenger platform
- 🎮 **Free Fire Tools**: Ranking points calculator and statistics
- 👥 **Lineup Management**: Manage team lineups and player information
- 🔐 **Key Generation**: Secure API key generation system

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   ├── bot/
│   │   │   │   ├── BotMain.kt
│   │   │   │   ├── handlers/
│   │   │   │   ├── commands/
│   │   │   │   ├── plugins/
│   │   │   │   ├── utils/
│   │   │   │   └── config/
│   │   │   └── webhook/
│   │   │       └── MessengerWebhook.kt
│   │   └── resources/
│   │       └── config.yml
│   └── test/
├── freefire/
│   ├── ranking/
│   │   └── RankingCalculator.kt
│   ├── tools/
│   │   └── FFTools.kt
│   └── models/
│       └── PlayerData.kt
├── lineup/
│   ├── LineupManager.kt
│   ├── models/
│   │   ├── Team.kt
│   │   └── Player.kt
│   └── database/
│       └── LineupDB.kt
├── keygen/
│   ├── KeyGenerator.kt
│   ├── KeyValidator.kt
│   └── encryption/
│       └── KeyEncryption.kt
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── .gitignore
```

## Installation

### Prerequisites
- Java 11+
- Gradle 7.0+
- Mirai Core 2.12+

### Setup

```bash
# Clone the repository
git clone https://github.com/hiesunhnaa/BOT-TL-ESPORT.git
cd BOT-TL-ESPORT.

# Build the project
gradle build

# Run the bot
gradle run
```

## Configuration

Edit `src/main/resources/config.yml`:

```yaml
bot:
  qq_id: YOUR_QQ_ID
  password: YOUR_PASSWORD
  mirai_core_path: ./mirai

webhook:
  messenger_token: YOUR_MESSENGER_TOKEN
  verify_token: YOUR_VERIFY_TOKEN
  port: 8080

freefire:
  api_base: https://freefire.api.endpoint
  api_key: YOUR_API_KEY

lineup:
  database_url: jdbc:sqlite:lineup.db

keygen:
  secret_key: YOUR_SECRET_KEY
  algorithm: AES_256
```

## Usage

### Commands

- `/ff rank <player>` - Get Free Fire player ranking
- `/ff calculate <kills> <assists> <damage>` - Calculate ranking points
- `/lineup add <player>` - Add player to lineup
- `/lineup view` - View current lineup
- `/lineup export` - Export lineup data
- `/key generate` - Generate new API key
- `/key validate <key>` - Validate API key

### Messenger Webhook

The bot listens on `http://localhost:8080/webhook/messenger`

## API Reference

### Ranking Calculator

```kotlin
val calculator = RankingCalculator()
val points = calculator.calculatePoints(
    kills = 10,
    assists = 5,
    damage = 1200,
    survival = 25
)
```

### Lineup Management

```kotlin
val manager = LineupManager()
manager.addPlayer(team = "TL", player = Player(name = "Player1", role = "Sniper"))
val lineup = manager.getLineup("TL")
```

### Key Generation

```kotlin
val keyGen = KeyGenerator()
val key = keyGen.generateKey()
val isValid = keyGen.validateKey(key)
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## License

MIT License - See LICENSE file for details

## Support

For issues and questions, please open an issue on GitHub.

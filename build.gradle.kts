plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("application")
}

group = "com.tlesport"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.mirai.mamoe.com/releases")
}

dependencies {
    // Mirai Core
    implementation("net.mamoe:mirai-core:2.15.0")
    implementation("net.mamoe:mirai-console:2.15.0")
    
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    
    // Web Framework
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    
    // Database
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    
    // Encryption
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    
    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("org.slf4j:slf4j-api:2.0.5")
    
    // Configuration
    implementation("com.charleskorn.kaml:kaml:0.54.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0")
}

application {
    mainClass.set("com.tlesport.bot.BotMainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

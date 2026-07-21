package com.tlesport.lineup.models

data class Team(
    val id: String,
    val name: String = "Default Team",
    val players: MutableList<Player> = mutableListOf(),
    val createdDate: Long = System.currentTimeMillis()
) {
    fun addPlayer(player: Player) {
        players.add(player)
    }
    
    fun removePlayer(player: Player) {
        players.remove(player)
    }
    
    fun getPlayerCount(): Int = players.size
    
    override fun toString(): String {
        return "Team(id=$id, name=$name, players=${players.size})\n" +
            players.mapIndexed { index, player -> "  ${index + 1}. $player" }.joinToString("\n")
    }
}

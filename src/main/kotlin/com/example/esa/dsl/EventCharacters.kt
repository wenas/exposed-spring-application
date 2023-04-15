package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable

object EventCharacters : IntIdTable("event_characters") {
    val gameEventId = reference("game_event_id", GameEvents)
    val characterName = varchar("character_name", 255)
    val characterClass = varchar("character_class", 255)
    val ability = varchar("ability", 255)
}



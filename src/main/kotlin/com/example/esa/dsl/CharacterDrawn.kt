package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object CharacterDrawn : IntIdTable("character_drawn") {
    val userId = reference("user_id", Users)
    val eventCharacterId = reference("event_character_id", EventCharacters)
    val drawnDate = datetime("drawn_date")
}
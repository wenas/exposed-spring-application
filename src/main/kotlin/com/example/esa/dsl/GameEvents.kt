package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object GameEvents : IntIdTable("game_events") {
    val gameInfoId = reference("game_info_id", GameInfo)
    val eventName = varchar("event_name", 255)
    val eventStartDate = datetime("event_start_date")
    val eventEndDate = datetime("event_end_date")
}
package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable

object GameInfo : IntIdTable("game_info") {
    val gameName = varchar("game_name", 255)
    val companyName = varchar("company_name", 255)
}
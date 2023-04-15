package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Users : IntIdTable("users") {
    val username = varchar("username", 255).uniqueIndex()
    val createdAt = datetime("created_at")
}
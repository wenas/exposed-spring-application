package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserActive : IntIdTable("user_active", "user_id") {
    val createdAt = datetime("created_at")
}
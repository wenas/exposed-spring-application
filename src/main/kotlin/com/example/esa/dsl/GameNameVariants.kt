package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object GameNameVariants : IntIdTable() {
    val gameInfoId = reference("game_info_id", GameInfo, onDelete = ReferenceOption.CASCADE)
    val nameVariant = varchar("name_variant", 255)
}
package com.example.esa.repository

import org.jetbrains.exposed.sql.CharColumnType
import org.jetbrains.exposed.sql.ExpressionWithColumnType
import org.jetbrains.exposed.sql.QueryBuilder
import org.jetbrains.exposed.sql.append
import java.time.LocalDate

class DateFormat<T : ExpressionWithColumnType<LocalDate>>(private val exp: T, private val format: String) :
    org.jetbrains.exposed.sql.Function<String>(CharColumnType()) {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        append("to_char(", exp, ", '$format')")
    }
}
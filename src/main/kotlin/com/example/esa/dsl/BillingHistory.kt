package com.example.esa.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date


object BillingHistory: IntIdTable("billing_history") {
    val userId = reference("user_id", Users)
    val purchaseDate = date("purchase_date")
    val gameName = varchar("game_name", 255)
    val purchasedItem = varchar("purchased_item", 255)
    val amount = integer("amount")
}

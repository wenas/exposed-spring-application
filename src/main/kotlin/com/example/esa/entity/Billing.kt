package com.example.esa.entity

import java.time.LocalDate

data class Billing(
    val purchaseDate: LocalDate,
    val gameName: String,
    val purchasedItem: String,
    val amount: Int,
) {
}
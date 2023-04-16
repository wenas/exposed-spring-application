package com.example.esa.dto

import java.time.LocalDate

data class Billing(
    val purchaseDate: LocalDate,
    val gameName: String,
    val purchasedItem: String,
    val amount: Int,
) {
}
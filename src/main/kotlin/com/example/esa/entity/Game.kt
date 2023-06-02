package com.example.esa.entity

data class Game(
    val id: Int,
    val gameName: String,
    val companyName: String,
    val nameVariants: List<String>
)

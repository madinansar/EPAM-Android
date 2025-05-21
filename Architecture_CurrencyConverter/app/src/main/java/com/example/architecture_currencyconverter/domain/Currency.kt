package com.example.architecture_currencyconverter.domain

data class Currency (
    val name: String,
    val currentRate: Double,
    val change24h: Double
)

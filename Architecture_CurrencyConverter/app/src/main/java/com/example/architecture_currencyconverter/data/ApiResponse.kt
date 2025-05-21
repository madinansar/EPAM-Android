package com.example.architecture_currencyconverter.data

data class ApiResponse(
    val base: String,
    val rates: Map<String, Double>,
    val date: String
)
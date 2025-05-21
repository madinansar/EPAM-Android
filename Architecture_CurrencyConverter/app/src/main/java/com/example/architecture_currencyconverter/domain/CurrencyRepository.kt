package com.example.architecture_currencyconverter.domain

interface CurrencyRepository {
    suspend fun getCurrencies(base: String) : List<Currency>
}
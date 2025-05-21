package com.example.architecture_currencyconverter.presentation

import com.example.architecture_currencyconverter.domain.Currency

data class CurrencyUiState(
    val isLoading: Boolean = false,
    val currencies: List<Currency> = emptyList(),
    val error: String? = null
)
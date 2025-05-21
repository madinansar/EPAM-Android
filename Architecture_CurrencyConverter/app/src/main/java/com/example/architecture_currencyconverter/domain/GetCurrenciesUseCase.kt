package com.example.architecture_currencyconverter.domain

class GetCurrenciesUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(base: String = "EUR") = repository.getCurrencies(base)
}
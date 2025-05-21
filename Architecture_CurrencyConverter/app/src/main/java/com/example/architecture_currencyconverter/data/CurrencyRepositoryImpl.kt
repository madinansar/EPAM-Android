package com.example.architecture_currencyconverter.data

import com.example.architecture_currencyconverter.domain.Currency
import com.example.architecture_currencyconverter.domain.CurrencyRepository
import java.time.LocalDate

class CurrencyRepositoryImpl(
    private val api: ExchangeRateApi,
//    private val api: MockExchangeRateApi,
    private val apiKey: String = "77e5cf9d6fa0aa1c922445622b319ed8"
) : CurrencyRepository{

    override suspend fun getCurrencies(base: String): List<Currency> {
        val todayRates = api.getLatestRates(apiKey).rates
        val yesterday = LocalDate.now().minusDays(1).toString()

        val yesterdayRates = api.getHistoricalRates(yesterday, apiKey).rates

        return todayRates.map { (currency, todayRate) ->
            val yesterdayRate = yesterdayRates[currency] ?: todayRate
            val change24h = ((todayRate - yesterdayRate) / yesterdayRate) * 100
            Currency(
                name = currency,
                currentRate = todayRate,
                change24h = change24h
            )
        }
    }
}
package com.example.architecture_currencyconverter.data

class MockExchangeRateApi : ExchangeRateApi {

    override suspend fun getLatestRates(apiKey: String): ApiResponse {
        return ApiResponse(
            base = "EUR",
            date = "2025-05-21",
            rates = mapOf(
                "USD" to 100.0,
                "GBP" to 150.0,
                "JPY" to 200.0
            )
        )
    }

    override suspend fun getHistoricalRates(date: String, apiKey: String): ApiResponse {
        return ApiResponse(
            base = "EUR",
            date = date,
            rates = mapOf(
                "USD" to 200.0,
                "GBP" to 50.0,
                "JPY" to 250.0
            )
        )
    }
}
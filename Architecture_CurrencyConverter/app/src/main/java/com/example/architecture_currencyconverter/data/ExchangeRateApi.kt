package com.example.architecture_currencyconverter.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") apiKey: String
    ): ApiResponse

    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("access_key") apiKey: String
    ): ApiResponse
}
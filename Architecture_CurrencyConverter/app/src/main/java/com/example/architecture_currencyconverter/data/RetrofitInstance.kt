package com.example.architecture_currencyconverter.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.exchangeratesapi.io/v1/"

    val api: ExchangeRateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }
}
package com.example.architecture_currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.architecture_currencyconverter.domain.GetCurrenciesUseCase


class CurrencyViewModelFactory(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(getCurrenciesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
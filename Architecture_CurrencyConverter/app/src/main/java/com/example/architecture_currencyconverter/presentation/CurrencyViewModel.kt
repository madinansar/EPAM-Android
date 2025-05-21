package com.example.architecture_currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecture_currencyconverter.domain.GetCurrenciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyUiState())
    val state: StateFlow<CurrencyUiState> = _state

    fun loadCurrencies(base: String = "EUR") {
        viewModelScope.launch {
            _state.value = CurrencyUiState(isLoading = true)
            try {
                _state.value = CurrencyUiState(currencies = getCurrenciesUseCase())
            } catch (e: Exception) {
                _state.value = CurrencyUiState(error = e.message)
            }
        }
    }
}
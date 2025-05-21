package com.example.architecture_currencyconverter.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.architecture_currencyconverter.data.CurrencyRepositoryImpl
import com.example.architecture_currencyconverter.data.MockExchangeRateApi
import com.example.architecture_currencyconverter.data.RetrofitInstance
import com.example.architecture_currencyconverter.databinding.ActivityMainBinding
import com.example.architecture_currencyconverter.domain.GetCurrenciesUseCase
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mockApi = MockExchangeRateApi()
        val repository = CurrencyRepositoryImpl(mockApi)
//        val repository = CurrencyRepositoryImpl(RetrofitInstance.api)

        val useCase = GetCurrenciesUseCase(repository)
        val factory = CurrencyViewModelFactory(useCase)
        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                Log.d("MainActivity", "State: $state")

                if (state.error != null) {
                    Log.e("MainActivity", "Error: ${state.error}")
                }

                if (state.error == null && state.currencies.isNotEmpty()) {
                    currencyAdapter = CurrencyAdapter(state.currencies)
                    recyclerView.adapter = currencyAdapter
                }
            }
        }
        viewModel.loadCurrencies("EUR")
    }
}

package com.example.architecture_currencyconverter.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.architecture_currencyconverter.R
import com.example.architecture_currencyconverter.domain.Currency

class CurrencyAdapter(private val currencies: List<Currency>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount(): Int = currencies.size

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyName: TextView = itemView.findViewById(R.id.currencyName)
        private val currencyRate: TextView = itemView.findViewById(R.id.currencyRate)

        fun bind(currency: Currency) {
            currencyName.text = currency.name
            currencyRate.text = "Rate: ${currency.currentRate}"

            val bgColor = if (currency.change24h >= 0) Color.parseColor("#A5D6A7") // green
            else Color.parseColor("#EF9A9A") // red
            itemView.setBackgroundColor(bgColor)
        }
    }
}
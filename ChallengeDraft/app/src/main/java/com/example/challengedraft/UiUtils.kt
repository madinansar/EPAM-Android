package com.example.challengedraft

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.challengedraft.R

fun applyColorToButtons(context: Context, vararg buttons: Button) {
    val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val colorPref = sharedPref.getString("button_color", "blue")

    val colorRes = when (colorPref) {
        "blue" -> R.color.blue
        "orange" -> R.color.orange
        "green" -> R.color.green
        else -> R.color.blue
    }

    val color = ContextCompat.getColor(context, colorRes)
    buttons.forEach { it.setBackgroundColor(color) }
}

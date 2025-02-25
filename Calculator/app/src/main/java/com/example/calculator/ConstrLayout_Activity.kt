package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

//Q:AppCompatActivity() why doesnt work, what is it
class ConstrLayout_Activity : ComponentActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_constr_layout)

        textView = findViewById(R.id.textView)

        val buttons = listOf(
            R.id.one, R.id.two, R.id.three, R.id.four, R.id.five,
            R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.plus,
            R.id.minus, R.id.multiply
        )

        for (buttonID in buttons){
            val button = findViewById<Button>(buttonID)
            button.setOnClickListener {
                textView.append(button.text)
            }
        }

    }
}
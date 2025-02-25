package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearBtn = findViewById<Button>(R.id.linear)
        linearBtn.setOnClickListener{
            //Toast.makeText(this, "button was clicked", Toast.LENGTH_SHORT).show()
            val intent1 = Intent(this, LinearLayout_Activity::class.java)
            startActivity(intent1)
            Toast.makeText(this, "linear button was clicked", Toast.LENGTH_SHORT).show()
        }


        val constrBtn = findViewById<Button>(R.id.constraint)
        constrBtn.setOnClickListener{
            //Toast.makeText(this, "button was clicked", Toast.LENGTH_SHORT).show()
            val intent2 = Intent(this, ConstrLayout_Activity::class.java)
            startActivity(intent2)
            Toast.makeText(this, "constr button was clicked", Toast.LENGTH_SHORT).show()
        }

        val frameBtn = findViewById<Button>(R.id.frame)
        frameBtn.setOnClickListener{
            //Toast.makeText(this, "button was clicked", Toast.LENGTH_SHORT).show()
            val intent3 = Intent(this, FrameLayout_Activity::class.java)
            startActivity(intent3)
            Toast.makeText(this, "frame button was clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
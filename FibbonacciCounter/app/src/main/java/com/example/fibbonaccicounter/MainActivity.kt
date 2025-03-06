package com.example.fibbonaccicounter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import com.example.fibbonaccicounter.databinding.ActivityMainBinding
import com.example.fibbonaccicounter.ui.theme.FibbonacciCounterTheme
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler by lazy{
        Handler(Looper.getMainLooper()) { message ->
            binding.tvCounter.text = message.data.getString(COUNTER_KEY)
            true
        }
    }
    private var counterThread: Thread? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            if (isRunning) {
                cancelCounter()
            } else {
                val userVal = binding.editText.text.toString().toInt()
                if (userVal >= 0) {
                    runCounter(userVal)
                } else {
                    binding.tvCounter.text = "Enter a valid number"
                }
            }
        }

    }

    private fun cancelCounter() {
        isRunning = false
        counterThread?.interrupt()
        counterThread = null
        resetUI()
    }

    private fun resetUI() {
        handler.post {
            binding.startBtn.text = "START"
            binding.editText.isEnabled = true
        }
    }


    private fun runCounter(n: Int){
        isRunning = true
        binding.editText.isEnabled = false
        binding.startBtn.text = "CANCEL"

        counterThread = Thread {
            for(i in 0..n){
                if (!isRunning) break
                var showedText = ""
                if (i == n){
                    showedText = "Result is ${fibCounter(n)}"
                    handler.post { resetUI() } // Ensure UI is reset properly
                } else {
                    showedText = "Current is $i"
                }

                val message = Message().apply {
                    data = bundleOf(COUNTER_KEY to showedText)
                }
                handler.sendMessage(message)
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    handler.post {
                        resetUI()
                        binding.tvCounter.text = ""
                    }
                    break
                }
            }
            isRunning = false

        }
        counterThread?.start()
    }


    companion object {
        private const val COUNTER_KEY = "COUNTER_KEY"
    }
}

//1, 2, 3, 4, 5, 6, 7,  8, 9
//1, 1, 2, 3, 5, 8, 13, 21
fun fibCounter(n: Int): Int {
    if (n == 0) return 0
    if (n == 1) return 1
    var a = 0
    var b = 1
    var result = 1
    for (i in 2..n) {
        result = a + b
        a = b
        b = result
    }
    return result
}

package com.example.fibbonaccicounter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.fibbonaccicounter.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentCount = 0
    private var userValue = 0
    private var isRunning = false

    private var a = 0
    private var b = 1
    private var fibValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //restore:
        savedInstanceState?.let {
            currentCount = it.getInt("CURRENT_COUNT", 0)
            userValue = it.getInt("USER_VALUE", 0)
            isRunning = it.getBoolean("IS_RUNNING", false)
            a = it.getInt("FIB_A", 0)
            b = it.getInt("FIB_B", 1)
            fibValue = it.getInt("FIB_VALUE", 0)
            binding.tvCounter.text = it.getString("STATE_TEXT", "default")

            if (isRunning) {
                runCounter(userValue, resume = true)
            }
        }

        binding.startBtn.setOnClickListener {
            if (isRunning) {
                cancelCounter()
                binding.tvCounter.text = ""
            } else {
                val userVal = binding.editText.text.toString().toIntOrNull() ?: -1
                if (userVal >= 0) {
                    userValue = userVal
                    runCounter(userValue)
                } else {
                    binding.tvCounter.text = "Enter a valid number"
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_COUNT", currentCount)
        outState.putInt("USER_VALUE", userValue)
        outState.putBoolean("IS_RUNNING", isRunning)
        outState.putString("STATE_TEXT", binding.tvCounter.text.toString())

        outState.putInt("FIB_A", a)
        outState.putInt("FIB_B", b)
        outState.putInt("FIB_VALUE", fibValue)
    }

    private fun cancelCounter() {
        isRunning = false
        counterThread?.interrupt()
        resetUI()
    }

    private fun resetUI() {
        binding.startBtn.text = "START"
        binding.editText.isEnabled = true
    }

    private var counterThread: Thread? = null

    private fun runCounter(n: Int, resume: Boolean = false) {
        isRunning = true
        binding.editText.isEnabled = false
        binding.startBtn.text = "CANCEL"

        if (!resume) {
            a = 0
            b = 1
            fibValue = 0
        }

        counterThread = Thread {
            for (i in (if (resume) currentCount+1 else 0)..n) {
                if (!isRunning) break

                fibValue = if (i == 0) 0 else if (i == 1) 1 else a + b
                a = b
                b = fibValue

                currentCount = i
                runOnUiThread {
                    binding.tvCounter.text = "Current: $i, Fibonacci: $fibValue"
                }
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException){
                    break
                }
            }
            isRunning = false
            runOnUiThread { resetUI() }
        }
        counterThread?.start()
    }

}





//    private fun runCounter(n: Int, resume: Boolean = false) {
//        isRunning = true
//        binding.editText.isEnabled = false
//        binding.startBtn.text = "CANCEL"
//
//        job = lifecycleScope.launch {
//            for (i in (if (resume) currentCount else 0)..n) {
//                currentCount = i
//                if (!isRunning) break
//                binding.tvCounter.text = "Current: $i"
//                delay(500)
//
//                if (i == n) {
//                    val result = withContext(Dispatchers.Default) { fibCounter(n) }
//                    withContext(Dispatchers.Main) {
//                        binding.tvCounter.text = "Result is $result"
//                        resetUI()
//                    }
//                }
//            }
//            isRunning = false
//        }
//    }

//    private fun runCounter(n: Int, resume: Boolean = false) {
//        isRunning = true
//        binding.editText.isEnabled = false
//        binding.startBtn.text = "CANCEL"
//
//        var a = 0
//        var b = 1
//        var fibValue = 0 // Store the current Fibonacci value
//
//        job = lifecycleScope.launch {
//            for (i in (if (resume) currentCount else 0)..n) {
//                if (!isRunning) break
//
//                // Compute Fibonacci at this step
//                fibValue = if (i == 0) 0 else if (i == 1) 1 else a + b
//
//                // Update UI with both counter and Fibonacci value
//                binding.tvCounter.text = "Current: $i, Fibonacci: $fibValue"
//
//                a = b
//                b = fibValue
//
//                currentCount = i // Store the current count for state restoration
//                delay(500) // Wait before next step
//            }
//            isRunning = false
//            resetUI()
//        }
//    }
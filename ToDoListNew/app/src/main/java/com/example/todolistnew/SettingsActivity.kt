package com.example.todolistnew

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolistnew.databinding.ActivitySettingsBinding
import com.example.todolistnew.databinding.EditListBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.radioGroupCompleted.setOnCheckedChangeListener { _, checkedId ->
            val color = when(checkedId) {
                R.id.RBgreen -> "green"
                R.id.RBblue -> "blue"
                R.id.RByellow -> "yellow"
                else -> "none"
            }
            prefs.edit().putString("completed_color", color).apply()
        }

        binding.radioGroupNot.setOnCheckedChangeListener { _, checkedId ->
            val color = when(checkedId) {
                R.id.RBbrown -> "brown"
                R.id.RBpink -> "pink"
                R.id.RBviolet -> "violet"
                else -> "none2"
            }
            prefs.edit().putString("non_completed_color", color).apply()
        }
    }


}
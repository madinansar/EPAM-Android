package com.example.challengedraft.views


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.challengedraft.R

class PrefsFragment : Fragment(R.layout.fragment_prefs) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        // Set previously selected color
        when (loadColor()) {
            "blue" -> radioGroup.check(R.id.radio_blue)
            "orange" -> radioGroup.check(R.id.radio_orange)
            "green" -> radioGroup.check(R.id.radio_green)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val colorPref = when (checkedId) {
                R.id.radio_blue -> "blue"
                R.id.radio_orange -> "orange"
                R.id.radio_green -> "green"
                else -> "blue"
            }
            saveColor(colorPref)
            updateBottomNavColor(colorPref)
            Toast.makeText(requireContext(), "Color preference saved", Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveColor(color: String) {
        val sharedPref = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("button_color", color).apply()
    }

    private fun loadColor(): String {
        val sharedPref = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("button_color", "blue") ?: "blue"
    }
    private fun updateBottomNavColor(colorPref: String) {
        val activity = requireActivity() as MainActivity
        activity.applyBottomNavColor(colorPref)
    }
}

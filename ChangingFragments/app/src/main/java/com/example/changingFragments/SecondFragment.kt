package com.example.changingFragments
import android.graphics.Color

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment


class SecondFragment : Fragment(R.layout.fragment_second) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBackgroundColor(Color.rgb(138, 43, 226))
    }
}
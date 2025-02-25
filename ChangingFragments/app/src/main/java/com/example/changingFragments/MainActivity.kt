package com.example.changingFragments

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

//How to know from which Activity to inherit?

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val firstFragment = FirstFragment()
            val secondFragment = SecondFragment()
            val thirdFragment = ThirdFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer1, firstFragment)
                .add(R.id.fragmentContainer2, secondFragment)
                .add(R.id.fragmentContainer3, thirdFragment)
                .commit()

        }

        //Toast.makeText(this, "FWD button was clicked", Toast.LENGTH_SHORT).show()

    }
}

package com.example.changingFragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import kotlin.random.Random


class FirstFragment : Fragment(R.layout.fragment_first) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swapBtn = view.findViewById<Button>(R.id.swapButton)
        var status = true
        swapBtn.setOnClickListener {
            val secondFragment = SecondFragment()
            val thirdFragment = ThirdFragment()

            if(status){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer2, thirdFragment)
                    .replace(R.id.fragmentContainer3, secondFragment)
                    .commit()
                status = !status
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer2, secondFragment)
                    .replace(R.id.fragmentContainer3, thirdFragment)
                    .commit()
                status = !status
            }
        }


        val changeBtn = view.findViewById<Button>(R.id.changeButton)

        changeBtn.setOnClickListener {

//            val secondFragment = SecondFragment()
//            val thirdFragment = ThirdFragment()
//
//            val randomColor2 = getRandomColor()
//            val randomColor3 = getRandomColor()
//
//
//            parentFragmentManager.commitNow {
//                replace(R.id.fragmentContainer2, secondFragment)
//                replace(R.id.fragmentContainer3, thirdFragment)
//            }
//
//            secondFragment.view?.setBackgroundColor(randomColor2)
//            thirdFragment.view?.setBackgroundColor(randomColor3)


            val secondFragment = parentFragmentManager.findFragmentById(R.id.fragmentContainer2)
            val thirdFragment = parentFragmentManager.findFragmentById(R.id.fragmentContainer3)

                secondFragment!!.requireView().setBackgroundColor(getRandomColor())
                thirdFragment!!.requireView().setBackgroundColor(getRandomColor())


            Toast.makeText(requireContext(), "Change Fragment clicked!", Toast.LENGTH_SHORT).show()

        }

    }
    private fun getRandomColor(): Int = Random.nextInt(Int.MAX_VALUE) or 0xFF000000.toInt()
//        val random = java.util.Random()
//        val r = random.nextInt(256)
//        val g = random.nextInt(256)
//        val b = random.nextInt(256)
//        return android.graphics.Color.rgb(r, g, b)



}